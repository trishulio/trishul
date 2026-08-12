import os
import re

for root, dirs, files in os.walk('modules'):
    for file in files:
        if file == 'ModelAccessorTest.java':
            path = os.path.join(root, file)
            print(f"Processing {path}")
            with open(path, 'r') as f:
                content = f.read()
            
            # Find the package definition
            pkg_match = re.search(r'package\s+([^;]+);', content)
            if not pkg_match:
                continue
            pkg = pkg_match.group(1)
            
            # Find all imports of target classes (excluding junit and PojoTestUtil)
            imports = re.findall(r'import\s+([a-zA-Z0-9._]+);', content)
            target_classes = []
            for imp in imports:
                if 'junit' not in imp and 'PojoTestUtil' not in imp:
                    target_classes.append(imp)
            
            # We will rewrite the test class to have one method per class
            new_content = f"package {pkg};\n\n"
            new_content += "import sh.trishul.test.util.PojoTestUtil;\n"
            new_content += "import org.junit.jupiter.api.Test;\n\n"
            for tc in target_classes:
                new_content += f"import {tc};\n"
            
            new_content += "\nclass ModelAccessorTest {\n"
            for tc in target_classes:
                simple_name = tc.split('.')[-1]
                new_content += f"  @Test\n"
                new_content += f"  void test{simple_name}() throws Exception {{\n"
                new_content += f"    PojoTestUtil.assertAccessors({simple_name}.class);\n"
                new_content += f"  }}\n\n"
            new_content += "}\n"
            
            with open(path, 'w') as f:
                f.write(new_content)

