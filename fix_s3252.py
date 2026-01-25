#!/usr/bin/env python3
import json
import re
import os

def extract_file_path(component):
    """Extract file path from component string"""
    if ':' in component:
        return component.split(':', 1)[1]
    return None

def fix_s3252_static_access(file_path, line_num, message):
    """Fix java:S3252 - Use static access for static members"""
    full_path = f'/Users/rishabmanocha/SourceCode/trishul/{file_path}'
    if not os.path.exists(full_path):
        return False
    
    try:
        # Extract the correct class name from message
        # Format: Use static access with "ClassName" for "FIELD_NAME"
        match = re.search(r'Use static access with "([^"]+)" for "([^"]+)"', message)
        if not match:
            return False
        
        correct_class = match.group(1).split('.')[-1]  # Get simple name
        field_name = match.group(2)
        
        with open(full_path, 'r') as f:
            lines = f.readlines()
        
        if line_num > len(lines):
            return False
        
        line_idx = line_num - 1
        original_line = lines[line_idx]
        
        # Look for patterns like "SomeClass.FIELD_NAME" and replace with "CorrectClass.FIELD_NAME"
        # This is complex because we need to find the wrong class reference
        # For now, let's look for any word followed by .FIELD_NAME
        pattern = rf'\b\w+\.{re.escape(field_name)}\b'
        replacement = f'{correct_class}.{field_name}'
        
        if re.search(pattern, original_line):
            modified_line = re.sub(pattern, replacement, original_line)
            if modified_line != original_line:
                lines[line_idx] = modified_line
                with open(full_path, 'w') as f:
                    f.writelines(lines)
                return True
    except Exception as e:
        print(f"Error fixing {file_path}:{line_num}: {e}")
    
    return False

def process_all_pages():
    """Process all S3252 issues from all pages"""
    all_issues = []
    for page in range(1, 7):
        with open(f'issues_page_{page}.json', 'r') as f:
            data = json.load(f)
            all_issues.extend([i for i in data['issues'] if i['rule'] == 'java:S3252'])
    
    print(f'Processing {len(all_issues)} S3252 issues...')
    
    fixed = 0
    failed = 0
    
    for issue in all_issues:
        file_path = extract_file_path(issue['component'])
        if file_path and 'line' in issue:
            if fix_s3252_static_access(file_path, issue['line'], issue['message']):
                fixed += 1
                if fixed % 20 == 0:
                    print(f'  Fixed {fixed} issues...')
            else:
                failed += 1
    
    print(f'\nResults:')
    print(f'  Fixed: {fixed}')
    print(f'  Failed: {failed}')

if __name__ == '__main__':
    process_all_pages()
