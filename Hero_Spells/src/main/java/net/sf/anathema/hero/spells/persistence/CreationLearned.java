package net.sf.anathema.hero.spells.persistence;

import com.google.common.base.Predicate;

public class CreationLearned implements Predicate<AttributedPto> {
  @Override
  public boolean apply(AttributedPto input) {
    return !input.isExperienceLearned;
  }
}
