package net.sf.anathema.character.equipment.item;

import net.sf.anathema.framework.view.SwingApplicationFrame;
import net.sf.anathema.interaction.Command;
import net.sf.anathema.lib.data.Condition;
import net.sf.anathema.lib.gui.dialog.userdialog.buttons.ConfigurableVetor;
import net.sf.anathema.lib.gui.list.veto.Vetor;
import net.sf.anathema.framework.environment.Resources;

public class DiscardChangesVetor implements Vetor {

  private final Condition preCondition;
  private final Resources resources;

  public DiscardChangesVetor(Resources resources, Condition preCondition) {
    this.resources = resources;
    this.preCondition = preCondition;
  }

  @Override
  public void requestPermissionFor(Command command) {
    if (!preCondition.isFulfilled()) {
      command.execute();
      return;
    }
    String messageText = resources.getString("Equipment.Creation.UnsavedChangesMessage.Text");
    final String okButtonText = resources.getString("Equipment.Creation.UnsavedChangesMessage.OKButton");
    ConfigurableVetor vetor = new ConfigurableVetor(SwingApplicationFrame.getParentComponent(), messageText,
            okButtonText);
    vetor.requestPermissionFor(command);
  }
}