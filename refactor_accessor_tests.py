import os
import re

def get_dummy_value(type_str):
    type_str = type_str.strip()
    if type_str == 'String': return '"testString"'
    elif type_str in ('Integer', 'int'): return '123'
    elif type_str in ('Long', 'long'): return '123L'
    elif type_str in ('Boolean', 'boolean'): return 'true'
    elif type_str in ('Double', 'double'): return '123.45'
    elif type_str in ('Float', 'float'): return '123.45f'
    elif type_str == 'LocalDateTime': return 'java.time.LocalDateTime.of(2000, 1, 1, 0, 0)'
    elif type_str == 'LocalDate': return 'java.time.LocalDate.of(2000, 1, 1)'
    elif type_str == 'LocalTime': return 'java.time.LocalTime.of(0, 0)'
    elif type_str == 'Instant': return 'java.time.Instant.EPOCH'
    elif type_str == 'UUID': return 'java.util.UUID.randomUUID()'
    elif type_str == 'URI': return 'java.net.URI.create("http://localhost")'
    elif type_str == 'URL': return 'java.net.URI.create("http://localhost").toURL()'
    elif type_str == 'BigDecimal': return 'new java.math.BigDecimal("123.45")'
    elif type_str == 'BigInteger': return 'new java.math.BigInteger("123")'
    elif type_str.startswith('List'): return 'java.util.List.of()'
    elif type_str.startswith('Set'): return 'java.util.Set.of()'
    elif type_str.startswith('Map'): return 'java.util.Map.of()'
    else: 
        if '<' in type_str:
            base_type = type_str.split('<')[0]
            return f"org.mockito.Mockito.mock({base_type}.class, org.mockito.Mockito.RETURNS_DEEP_STUBS)"
        return f"org.mockito.Mockito.mock({type_str}.class, org.mockito.Mockito.RETURNS_DEEP_STUBS)"

def process_class(java_file, class_name, existing_test_content="", imports_list=[]):
    with open(java_file, 'r') as f:
        content = f.read()

    # Find all setters: e.g. public void setX(Y y) or public MyClass setX(Y y)
    setter_pattern = re.compile(r'public\s+([\w<>.,\s]+?)\s+set([A-Z]\w*)\s*\(\s*([^)\s]+\s*[^)]*?)\s+(\w+)\s*\)\s*\{')
    
    setters = setter_pattern.findall(content)
    
    tests = []
    
    for ret_type, prop, param_type, param_name in setters:
        ret_type = ret_type.strip()
        param_type = param_type.strip()
        
        # Avoid duplicate generation
        test_method_sig = f"void testAccess{prop}()"
        if test_method_sig in existing_test_content:
            continue
            
        # Some param types have annotations, strip them. E.g. @Nullable String
        if ' ' in param_type:
            param_type = param_type.split(' ')[-1]
            
        getter_name = f"get{prop}"
        is_getter_name = f"is{prop}"
        
        has_getter = f"{getter_name}()" in content
        has_is_getter = f"{is_getter_name}()" in content
        
        if not (has_getter or has_is_getter):
            continue
            
        actual_getter = getter_name if has_getter else is_getter_name
        dummy_val = get_dummy_value(param_type)
        
        test_code = f"  @org.junit.jupiter.api.Test\n"
        test_code += f"  void testAccess{prop}() throws Exception {{\n"
        
        test_code += f"    {class_name} accessor = new {class_name}();\n"
        
        if "mock" in dummy_val or "new " in dummy_val or ".of(" in dummy_val or "randomUUID" in dummy_val:
            test_code += f"    {param_type} value = {dummy_val};\n"
            dummy_val_ref = "value"
        else:
            dummy_val_ref = dummy_val
        
        if ret_type == class_name:
            test_code += f"    org.junit.jupiter.api.Assertions.assertSame(accessor, accessor.set{prop}({dummy_val_ref}));\n"
        else:
            test_code += f"    accessor.set{prop}({dummy_val_ref});\n"
            
        test_code += f"    org.junit.jupiter.api.Assertions.assertEquals({dummy_val_ref}, accessor.{actual_getter}());\n"
        test_code += f"  }}\n"
        
        tests.append(test_code)
        
    return tests

def find_main_java_file(root_dir, class_name):
    for root, dirs, files in os.walk(root_dir):
        if f"{class_name}.java" in files:
            if '/src/main/java/' in root:
                return os.path.join(root, f"{class_name}.java")
    return None

def main():
    workspace_root = '.'
    model_accessor_pattern = re.compile(r'PojoTestUtil\.assertAccessors\(\s*([A-Za-z0-9_]+)\.class\s*\);')

    for root, dirs, files in os.walk(workspace_root):
        if 'ModelAccessorTest.java' in files:
            filepath = os.path.join(root, 'ModelAccessorTest.java')
            
            with open(filepath, 'r') as f:
                content = f.read()
                
            classes_to_test = model_accessor_pattern.findall(content)
            
            if not classes_to_test:
                continue
                
            module_root = filepath.split('/src/test/java/')[0]
            
            for class_name in classes_to_test:
                java_file = find_main_java_file(module_root, class_name)
                if not java_file:
                    print(f"Could not find main java file for {class_name} in {module_root}")
                    continue
                
                # Determine test file location
                pkg_match = re.search(r'package\s+([^;]+);', open(java_file).read())
                if not pkg_match:
                    continue
                    
                pkg = pkg_match.group(1)
                test_dir = os.path.join(module_root, 'src/test/java', pkg.replace('.', '/'))
                os.makedirs(test_dir, exist_ok=True)
                test_file = os.path.join(test_dir, f"{class_name}Test.java")
                
                existing_content = ""
                if os.path.exists(test_file):
                    with open(test_file, 'r') as tf:
                        existing_content = tf.read()
                        
                imports = re.findall(r'^import\s+.*?;', open(java_file).read(), re.MULTILINE)
                        
                tests = process_class(java_file, class_name, existing_content, imports)
                if not tests:
                    print(f"No new setters found for {class_name}")
                    continue
                
                if os.path.exists(test_file):
                    # Append tests
                    # Ensure imports are present
                    for imp in imports:
                        if imp not in existing_content:
                            existing_content = existing_content.replace(f"package {pkg};", f"package {pkg};\n{imp}")
                            
                    last_brace_idx = existing_content.rfind('}')
                    if last_brace_idx != -1:
                        new_content = existing_content[:last_brace_idx] + "\n" + "\n".join(tests) + "\n" + existing_content[last_brace_idx:]
                        with open(test_file, 'w') as f:
                            f.write(new_content)
                else:
                    # Create new test file
                    new_content = f"package {pkg};\n\n"
                    new_content += "\n".join(imports) + "\n\n"
                    new_content += f"class {class_name}Test {{\n"
                    new_content += "\n".join(tests)
                    new_content += "}\n"
                    with open(test_file, 'w') as f:
                        f.write(new_content)
                        
            # Finally remove ModelAccessorTest.java
            os.remove(filepath)
            print(f"Deleted {filepath}")

if __name__ == '__main__':
    main()
