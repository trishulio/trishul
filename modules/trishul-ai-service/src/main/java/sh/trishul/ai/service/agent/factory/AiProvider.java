package sh.trishul.ai.service.agent.factory;

public enum AiProvider {
  OPENAI, ANTHROPIC, GITHUB_COPILOT, OPENROUTER;

  public static AiProvider fromString(String provider) {
    for (AiProvider p : values()) {
      if (p.name().equalsIgnoreCase(provider)) {
        return p;
      }
    }
    throw new IllegalArgumentException("Unsupported AI Provider: " + provider);
  }
}
