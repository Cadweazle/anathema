package net.sf.anathema.character.main.view.repository;

import net.sf.anathema.character.main.template.HeroTemplate;
import net.sf.anathema.character.main.template.ITemplateType;
import net.sf.anathema.character.main.template.presentation.IPresentationProperties;

public interface ITemplateTypeAggregation {

  ITemplateType getTemplateType();

  IPresentationProperties getPresentationProperties();

  boolean contains(HeroTemplate template);
}