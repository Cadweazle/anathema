package net.sf.anathema.lib.gui;

import net.sf.anathema.lib.file.RelativePath;
import net.sf.anathema.lib.gui.icon.ImageProvider;
import net.sf.anathema.lib.gui.ui.ObjectUi;

import javax.swing.Icon;

public class ConfigurableSwingUI<T> implements ObjectUi<T> {

  private AgnosticUIConfiguration<T> configuration;

  public ConfigurableSwingUI(AgnosticUIConfiguration<T> configuration) {
    this.configuration = configuration;
  }

  @SuppressWarnings("StringEquality")
  @Override
  public Icon getIcon(T value) {
    RelativePath relativePath = configuration.getIconsRelativePath(value);
    if (relativePath == AgnosticUIConfiguration.NO_ICON) {
      return null;
    }
    return new ImageProvider().getImageIcon(relativePath);
  }

  @Override
  public String getLabel(T value) {
    return configuration.getLabel(value);
  }

  @Override
  public String getToolTipText(T value) {
    TooltipBuilder tooltip = new TooltipBuilder();
    configuration.configureTooltip(value, tooltip);
    return tooltip.build();
  }
}
