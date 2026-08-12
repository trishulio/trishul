package sh.trishul.ai.skill.model;

public interface AiSkillAccessor<T extends AiSkillAccessor<T>> {
  final String ATTR_SKILL = "skill";

  AiSkill getSkill();

  T setSkill(AiSkill skill);
}
