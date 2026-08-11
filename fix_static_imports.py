import re
import os

def process_file(filepath):
    with open(filepath, 'r') as f:
        content = f.read()

    # We want to find broken static imports like `import static BigDecimal.TEN;`
    # and replace them with `import static java.math.BigDecimal.TEN;`
    # The broken ones are the classes from our class_replacements
    
    corrections = [
        (r'import static LocalDateTime\.(\w+);', r'import static java.time.LocalDateTime.\1;'),
        (r'import static UUID\.(\w+);', r'import static java.util.UUID.\1;'),
        (r'import static LocalDate\.(\w+);', r'import static java.time.LocalDate.\1;'),
        (r'import static LocalTime\.(\w+);', r'import static java.time.LocalTime.\1;'),
        (r'import static URI\.(\w+);', r'import static java.net.URI.\1;'),
        (r'import static URL\.(\w+);', r'import static java.net.URL.\1;'),
        (r'import static BigDecimal\.(\w+);', r'import static java.math.BigDecimal.\1;')
    ]
    
    new_content = content
    for pat, rep in corrections:
        new_content = re.sub(pat, rep, new_content)
        
    if new_content != content:
        with open(filepath, 'w') as f:
            f.write(new_content)
            
    return new_content != content

changed = 0
for root, dirs, files in os.walk('modules'):
    for f in files:
        if f.endswith('.java'):
            if process_file(os.path.join(root, f)):
                changed += 1

print(f"Fixed {changed} files")
