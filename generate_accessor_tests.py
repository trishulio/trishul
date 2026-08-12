import os
import re

for module in os.listdir('modules'):
    module_path = os.path.join('modules', module)
    if not os.path.isdir(module_path):
        continue
    
    java_main_dir = os.path.join(module_path, 'src', 'main', 'java')
    if not os.path.exists(java_main_dir):
        continue
        
    classes_to_test = []
    
    for root, dirs, files in os.walk(java_main_dir):
        for file in files:
            if file.endswith('.java'):
                file_path = os.path.join(root, file)
                with open(file_path, 'r') as f:
                    content = f.read()
                
                # Check package
                pkg_match = re.search(r'package\s+([^;]+);', content)
                if not pkg_match:
                    continue
                pkg = pkg_match.group(1)
                
                # Exclude non-model packages
                skip_pkg_parts = ['service', 'controller', 'autoconfiguration', 'repository', 
                                   'mapper', 'exception', 'config', 'client', 'filter', 
                                   'holder', 'aspect', 'logger', 'validator', 'converter', 
                                   'query', 'join', 'spec', 'refresher', 'builder']
                if any(part in pkg.split('.') for part in skip_pkg_parts):
                    continue
                
                # Include model, dto, entity, pojo, types, value, status, or role packages
                ok_pkg_parts = ['model', 'dto', 'entity', 'pojo', 'types', 'value', 'status', 'role', 'salutation', 'currency', 'tax', 'unit']
                if not any(part in pkg.split('.') for part in ok_pkg_parts):
                    continue
                
                # Check if it is a class, record
                if 'interface ' in content or 'abstract class ' in content or 'enum ' in content:
                    continue
                if 'class ' not in content and 'record ' not in content:
                    continue
                
                # Get class name
                class_name = file[:-5]
                full_class_name = f"{pkg}.{class_name}"
                classes_to_test.append(full_class_name)
                
    if not classes_to_test:
        continue
        
    # Determine test directory and package
    # We will use the first class's package but in src/test/java
    first_class = classes_to_test[0]
    test_pkg = '.'.join(first_class.split('.')[:-1])
    test_dir = os.path.join(module_path, 'src', 'test', 'java', *test_pkg.split('.'))
    os.makedirs(test_dir, exist_ok=True)
    
    test_file_path = os.path.join(test_dir, 'ModelAccessorTest.java')
    print(f"Generating {test_file_path} with {len(classes_to_test)} classes")
    
    new_content = f"package {test_pkg};\n\n"
    new_content += "import sh.trishul.test.util.PojoTestUtil;\n"
    new_content += "import org.junit.jupiter.api.Test;\n\n"
    for tc in sorted(classes_to_test):
        new_content += f"import {tc};\n"
        
    new_content += "\nclass ModelAccessorTest {\n"
    for tc in sorted(classes_to_test):
        simple_name = tc.split('.')[-1]
        new_content += f"  @Test\n"
        new_content += f"  void test{simple_name}() throws Exception {{\n"
        new_content += f"    PojoTestUtil.assertAccessors({simple_name}.class);\n"
        new_content += f"  }}\n\n"
    new_content += "}\n"
    
    with open(test_file_path, 'w') as f:
        f.write(new_content)

