package net.sf.anathema.character.main.magic.charm.martial;

import net.sf.anathema.lib.util.Identifier;

public enum MartialArtsLevel implements Identifier {
  Mortal, Terrestrial, Celestial, Sidereal;

  @Override
  public String getId() {
    return name();
  }
}