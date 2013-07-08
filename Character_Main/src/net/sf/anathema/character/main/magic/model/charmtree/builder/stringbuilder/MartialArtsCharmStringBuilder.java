package net.sf.anathema.character.main.magic.model.charmtree.builder.stringbuilder;

import net.sf.anathema.character.main.magic.model.charms.MartialArtsUtilities;
import net.sf.anathema.character.main.magic.model.charm.ICharm;
import net.sf.anathema.character.main.magic.model.magic.IMagic;
import net.sf.anathema.character.main.magic.model.charm.MartialArtsLevel;
import net.sf.anathema.lib.gui.TooltipBuilder;
import net.sf.anathema.lib.resources.Resources;

public class MartialArtsCharmStringBuilder implements IMagicTooltipStringBuilder {
  private final Resources resources;

  public MartialArtsCharmStringBuilder(Resources resources) {
    this.resources = resources;
  }

  @Override
  public void buildStringForMagic(StringBuilder builder, IMagic magic, Object details) {
    if (magic instanceof ICharm && MartialArtsUtilities.isMartialArtsCharm((ICharm) magic)) {
      MartialArtsLevel level = MartialArtsUtilities.getLevel((ICharm) magic);
      builder.append(resources.getString("CharmTreeView.ToolTip.MartialArtsLevel"));
      builder.append(TooltipBuilder.ColonSpace);
      builder.append(resources.getString(level.getId()));
      builder.append(TooltipBuilder.HtmlLineBreak);
    }
  }
}