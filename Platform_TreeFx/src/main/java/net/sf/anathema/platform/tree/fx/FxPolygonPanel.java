package net.sf.anathema.platform.tree.fx;

import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import net.sf.anathema.framework.ui.Coordinate;
import net.sf.anathema.framework.ui.RGBColor;
import net.sf.anathema.platform.tree.display.DisplayPolygonPanel;
import net.sf.anathema.platform.tree.display.transform.AgnosticTransform;
import net.sf.anathema.platform.tree.view.MouseBorderClosure;
import net.sf.anathema.platform.tree.view.draw.GraphicsElement;
import net.sf.anathema.platform.tree.view.draw.InteractiveGraphicsElement;
import net.sf.anathema.platform.tree.view.interaction.Closure;
import net.sf.anathema.platform.tree.view.interaction.ElementContainer;
import net.sf.anathema.platform.tree.view.interaction.Executor;
import net.sf.anathema.platform.tree.view.interaction.MetaKey;
import net.sf.anathema.platform.tree.view.interaction.MouseButton;
import net.sf.anathema.platform.tree.view.interaction.MouseClickClosure;
import net.sf.anathema.platform.tree.view.interaction.MouseMotionClosure;
import net.sf.anathema.platform.tree.view.interaction.MousePressClosure;
import net.sf.anathema.platform.tree.view.interaction.MouseWheelClosure;
import net.sf.anathema.platform.tree.view.interaction.SpecialControlTrigger;

import java.util.ArrayList;
import java.util.List;

import static javafx.scene.Cursor.DEFAULT;
import static javafx.scene.Cursor.HAND;
import static javafx.scene.Cursor.MOVE;
import static javafx.scene.input.MouseEvent.MOUSE_CLICKED;
import static javafx.scene.input.MouseEvent.MOUSE_DRAGGED;
import static javafx.scene.input.MouseEvent.MOUSE_PRESSED;
import static net.sf.anathema.platform.tree.view.interaction.MouseButton.Other;
import static net.sf.anathema.platform.tree.view.interaction.MouseButton.Primary;
import static net.sf.anathema.platform.tree.view.interaction.MouseButton.Secondary;

public class FxPolygonPanel implements DisplayPolygonPanel {
  private final ElementContainer container = new ElementContainer();
  private final List<FxSpecialTrigger> specialControls = new ArrayList<>();
  private final Canvas canvas = new Canvas();
  private AgnosticTransform transform = new AgnosticTransform();

  public FxPolygonPanel() {
    canvas.getGraphicsContext2D().setFill(Color.WHITE);
  }

  @Override
  public void refresh() {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void revalidate() {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public SpecialControlTrigger addSpecialControl() {
    FxSpecialTrigger specialControl = new FxSpecialTrigger();
    specialControls.add(specialControl);
    specialControl.transformOriginalCoordinates(transform);
    specialControl.addTo(canvas);
    return specialControl;
  }

  @Override
  public void add(InteractiveGraphicsElement element) {
    container.add(element);
  }

  @Override
  public void add(GraphicsElement element) {
    container.add(element);
  }

  @Override
  public void changeCursor(Coordinate elementCoordinates) {
    container.onElementAtPoint(elementCoordinates).perform(new SetHandCursor()).orFallBackTo(new SetDefaultCursor());
  }

  @Override
  public void clear() {
    container.clear();
    for (FxSpecialTrigger specialControl : specialControls) {
      specialControl.remove();
    }
    specialControls.clear();
    refresh();
  }

  @Override
  public Executor onElementAtPoint(Coordinate elementCoordinates) {
    return container.onElementAtPoint(elementCoordinates);
  }

  @Override
  public void addMousePressListener(final MousePressClosure listener) {
    canvas.addEventHandler(MOUSE_PRESSED, new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        listener.mousePressed(determineCoordinate(event));
      }
    });
  }

  @Override
  public void addMouseClickListener(final MouseClickClosure listener) {
    canvas.addEventHandler(MOUSE_CLICKED, new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        MouseButton button = determineMouseButton(event);
        MetaKey key = determineMetaKey(event);
        listener.mouseClicked(button, key, determineCoordinate(event), event.getClickCount());
      }
    });
  }

  @Override
  public void addMouseWheelListener(final MouseWheelClosure listener) {
    canvas.addEventHandler(ScrollEvent.SCROLL, new EventHandler<ScrollEvent>() {
      @Override
      public void handle(ScrollEvent scrollEvent) {
        listener.mouseWheelMoved((int) scrollEvent.getTextDeltaY(), new Coordinate(scrollEvent.getX(), scrollEvent.getY()));
      }
    });
  }

  @Override
  public void addMouseBorderListener(final MouseBorderClosure listener) {
    canvas.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent mouseEvent) {
        listener.mouseEntered();
      }
    });
    canvas.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent mouseEvent) {
        listener.mouseExited();
      }
    });
  }

  @Override
  public void addMouseMotionListener(final MouseMotionClosure listener) {
    canvas.addEventHandler(MouseEvent.MOUSE_MOVED, new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent mouseEvent) {
        listener.mouseMoved(determineCoordinate(mouseEvent));
      }
    });
    canvas.addEventHandler(MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent mouseEvent) {
        listener.mouseDragged(determineMouseButton(mouseEvent), determineCoordinate(mouseEvent));
      }
    });
  }

  @Override
  public void setBackground(RGBColor color) {
    canvas.getGraphicsContext2D().setFill(FxColorUtils.toFxColor(color));
  }

  @Override
  public void showMoveCursor() {
    canvas.setCursor(MOVE);
  }

  @Override
  public void resetAllTooltips() {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void setTransformation(AgnosticTransform transform) {
    this.transform = transform;
    refresh();
  }

  @Override
  public int getWidth() {
    return (int) canvas.getWidth();
  }

  @Override
  public int getHeight() {
    return (int) canvas.getHeight();
  }

  @Override
  public void setToolTipText(String toolTip) {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  private MouseButton determineMouseButton(MouseEvent event) {
    if (event.isPrimaryButtonDown()) {
      return Primary;
    } else if (event.isSecondaryButtonDown()) {
      return Secondary;
    }
    return Other;
  }

  private MetaKey determineMetaKey(MouseEvent event) {
    MetaKey key;
    if (event.isControlDown()) {
      key = MetaKey.CTRL;
    } else {
      key = MetaKey.NONE;
    }
    return key;
  }

  private Coordinate determineCoordinate(MouseEvent mouseEvent) {
    return new Coordinate(mouseEvent.getX(), mouseEvent.getY());
  }

  private class SetHandCursor implements Closure {
    @Override
    public void execute(InteractiveGraphicsElement polygon) {
      canvas.setCursor(HAND);
    }
  }

  private class SetDefaultCursor implements Runnable {
    @Override
    public void run() {
      canvas.setCursor(DEFAULT);
    }
  }
}