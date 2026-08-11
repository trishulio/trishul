import re
import os

def process_file(filepath, dry_run=True):
    with open(filepath, 'r') as f:
        content = f.read()
        
    replacements = [
        (r'org\.junit\.jupiter\.api\.Assertions\.(\w+)', r'import static org.junit.jupiter.api.Assertions.\1;', r'\1'),
        (r'org\.mockito\.Mockito\.(\w+)', r'import static org.mockito.Mockito.\1;', r'\1'),
    ]
    
    class_replacements = [
        (r'java\.time\.LocalDateTime\.(\w+)', r'import java.time.LocalDateTime;', r'LocalDateTime.\1'),
        (r'java\.util\.UUID\.(\w+)', r'import java.util.UUID;', r'UUID.\1'),
        (r'java\.time\.LocalDate\.(\w+)', r'import java.time.LocalDate;', r'LocalDate.\1'),
        (r'java\.time\.LocalTime\.(\w+)', r'import java.time.LocalTime;', r'LocalTime.\1'),
        (r'java\.net\.URI\.(\w+)', r'import java.net.URI;', r'URI.\1'),
        (r'java\.net\.URL\.(\w+)', r'import java.net.URL;', r'URL.\1'),
        (r'java\.math\.BigDecimal\.(\w+)', r'import java.math.BigDecimal;', r'BigDecimal.\1')
    ]

    imports_to_add = set()
    new_content = content
    
    for pat, imp_pat, rep in replacements:
        for match in re.finditer(pat, new_content):
            imports_to_add.add(imp_pat.replace(r'\1', match.group(1)))
        new_content = re.sub(pat, rep, new_content)
        
    for pat, imp_pat, rep in class_replacements:
        for match in re.finditer(pat, new_content):
            imports_to_add.add(imp_pat.replace(r'\1', match.group(1)))
        new_content = re.sub(pat, rep, new_content)
        
    if imports_to_add and new_content != content:
        existing_imports = set(re.findall(r'^import\s+[^;]+;', new_content, re.MULTILINE))
        imports_to_add = imports_to_add - existing_imports
        
        if not dry_run:
            if imports_to_add:
                pkg_match = re.search(r'^package\s+[^;]+;', new_content, re.MULTILINE)
                if pkg_match:
                    insert_pos = pkg_match.end()
                    import_str = '\n\n' + '\n'.join(sorted(list(imports_to_add)))
                    new_content = new_content[:insert_pos] + import_str + new_content[insert_pos:]
                else:
                    import_str = '\n'.join(sorted(list(imports_to_add))) + '\n\n'
                    new_content = import_str + new_content
                    
            with open(filepath, 'w') as f:
                f.write(new_content)
            
    return new_content != content

changed = 0
for root, dirs, files in os.walk('modules'):
    for f in files:
        if f.endswith('.java'):
            if process_file(os.path.join(root, f), dry_run=False):
                changed += 1

print(f"Changed {changed} files")
