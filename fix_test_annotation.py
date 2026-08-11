import re
import os

def process_file(filepath):
    with open(filepath, 'r') as f:
        content = f.read()

    # The pattern we want to replace
    pattern = r'@org\.junit\.jupiter\.api\.Test\b'
    
    if re.search(pattern, content):
        # Replace it
        new_content = re.sub(pattern, '@Test', content)
        
        # Check if the import already exists
        import_stmt = 'import org.junit.jupiter.api.Test;'
        if import_stmt not in new_content:
            # Add the import statement
            # find the package declaration
            pkg_match = re.search(r'^package\s+[^;]+;', new_content, re.MULTILINE)
            if pkg_match:
                insert_pos = pkg_match.end()
                new_content = new_content[:insert_pos] + '\n\n' + import_stmt + new_content[insert_pos:]
            else:
                new_content = import_stmt + '\n\n' + new_content
                
        with open(filepath, 'w') as f:
            f.write(new_content)
            
        return True
    return False

changed = 0
for root, dirs, files in os.walk('modules'):
    for f in files:
        if f.endswith('.java'):
            if process_file(os.path.join(root, f)):
                changed += 1

print(f"Fixed {changed} files")
