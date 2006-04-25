package net.sf.anathema.character.generic.impl.magic.persistence;

import net.sf.anathema.character.generic.impl.magic.Charm;
import net.sf.anathema.character.generic.magic.ICharm;
import net.sf.anathema.character.generic.rules.IExaltedRuleSet;
import net.sf.anathema.lib.exception.PersistenceException;
import net.sf.anathema.lib.util.IIdentificate;

public interface ICharmCache {

  public abstract ICharm[] getCharms(IIdentificate type, IExaltedRuleSet ruleset) throws PersistenceException;

  public Charm searchCharm(String charmId);
}