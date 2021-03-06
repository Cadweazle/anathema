package net.sf.anathema.lib.gui.dialog.userdialog;

import net.sf.anathema.lib.gui.dialog.core.AbstractGenericDialogConfiguration;
import net.sf.anathema.lib.gui.dialog.core.DialogHeaderPanelConfiguration;
import net.sf.anathema.lib.gui.dialog.userdialog.buttons.DialogButtonConfigurationFactory;
import net.sf.anathema.lib.gui.dialog.userdialog.buttons.IDialogButtonConfiguration;
import net.sf.anathema.lib.gui.dialog.userdialog.page.IDialogPage;

public class DefaultDialogConfiguration<P extends IDialogPage> extends AbstractGenericDialogConfiguration implements IDialogConfiguration<P> {
  public static <P extends IDialogPage> DefaultDialogConfiguration<P> createWithOkAndCancel(P dialogPage) {
    return new DefaultDialogConfiguration<>(dialogPage, DialogButtonConfigurationFactory.createOkCancel());
  }

  public static <P extends IDialogPage> DefaultDialogConfiguration<P> createWithOkOnly(P dialogPage) {
    return new DefaultDialogConfiguration<>(dialogPage, DialogButtonConfigurationFactory.createOkOnly());
  }

  private final P dialogPage;

  public DefaultDialogConfiguration(P dialogPage, IDialogButtonConfiguration buttonConfiguration) {
    super(buttonConfiguration, DialogHeaderPanelConfiguration.createVisible());
    this.dialogPage = dialogPage;
  }

  @Override
  public P getDialogPage() {
    return dialogPage;
  }
}