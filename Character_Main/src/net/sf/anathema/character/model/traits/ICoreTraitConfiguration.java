package net.sf.anathema.character.model.traits;

import net.sf.anathema.character.generic.traits.groups.IIdentifiedTraitTypeGroup;
import net.sf.anathema.character.library.trait.ITraitCollection;
import net.sf.anathema.character.library.trait.specialties.ISpecialtiesConfiguration;

public interface ICoreTraitConfiguration extends ITraitCollection {

  ISpecialtiesConfiguration getSpecialtyConfiguration();

  IIdentifiedTraitTypeGroup[] getAbilityTypeGroups();
}