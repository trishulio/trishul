# GitHub Repository settings
data "github_repository" "trishul" {
  name = var.repo_name
}

# Branch protection for main branch
resource "github_branch_protection" "main" {
  repository_id = data.github_repository.trishul.node_id
  pattern       = "main"
  
  required_status_checks {
    strict   = true
    contexts = ["continuous-integration/jenkins/pr-merge"]
  }

  required_pull_request_reviews {
    dismiss_stale_reviews  = true
    require_code_owner_reviews = true
  }
  
  enforce_admins = true
}

