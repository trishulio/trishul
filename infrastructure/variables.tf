variable "github_token" {
  type        = string
  description = "GitHub Personal Access Token"
  sensitive   = true
}

variable "repo_name" {
  type        = string
  description = "Repository Name"
  default     = "trishul"
}

variable "repo_owner" {
  type        = string
  description = "Repository Owner"
  default     = "trishulio"
}
