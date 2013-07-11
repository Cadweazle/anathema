package net.sf.anathema.hero.spells.display;

import net.sf.anathema.character.main.magic.model.spells.CircleType;
import net.sf.anathema.hero.magic.display.MagicLearnProperties;
import net.sf.anathema.hero.magic.display.MagicLearnView;
import net.sf.anathema.lib.control.ObjectValueListener;
import net.sf.anathema.lib.util.Identifier;

public interface SpellView {

  void initGui(Identifier[] circles, ISpellViewProperties properties);

  MagicLearnView addMagicLearnView(MagicLearnProperties properties);

  void addCircleSelectionListener(ObjectValueListener<CircleType> listener);
}