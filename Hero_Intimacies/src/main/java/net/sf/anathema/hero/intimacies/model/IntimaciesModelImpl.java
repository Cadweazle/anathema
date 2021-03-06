package net.sf.anathema.hero.intimacies.model;

import com.google.common.base.Strings;
import net.sf.anathema.character.main.library.removableentry.AbstractRemovableEntryModel;
import net.sf.anathema.character.main.library.trait.Trait;
import net.sf.anathema.character.main.traits.TraitType;
import net.sf.anathema.character.main.traits.ValuedTraitType;
import net.sf.anathema.character.main.traits.types.OtherTraitType;
import net.sf.anathema.character.main.traits.types.VirtueType;
import net.sf.anathema.hero.experience.ExperienceModelFetcher;
import net.sf.anathema.hero.framework.HeroEnvironment;
import net.sf.anathema.hero.intimacies.points.IntimaciesBonusPointCalculator;
import net.sf.anathema.hero.model.Hero;
import net.sf.anathema.hero.model.change.ChangeAnnouncer;
import net.sf.anathema.hero.model.change.ChangeFlavor;
import net.sf.anathema.hero.model.change.FlavoredChangeListener;
import net.sf.anathema.hero.model.change.RemovableEntryChangeAdapter;
import net.sf.anathema.hero.model.change.UnspecifiedChangeListener;
import net.sf.anathema.hero.points.PointModelFetcher;
import net.sf.anathema.hero.traits.TraitChangeFlavor;
import net.sf.anathema.hero.traits.TraitModelFetcher;
import net.sf.anathema.lib.control.ChangeListener;
import net.sf.anathema.lib.util.Identifier;
import org.jmock.example.announcer.Announcer;

public class IntimaciesModelImpl extends AbstractRemovableEntryModel<Intimacy> implements IntimaciesModel {

  private final Announcer<ChangeListener> announcer = Announcer.to(ChangeListener.class);
  private String name;
  private Hero hero;

  @Override
  public Identifier getId() {
    return ID;
  }

  @Override
  public void initialize(HeroEnvironment environment, Hero hero) {
    this.hero = hero;
    PointModelFetcher.fetch(hero).addBonusPointCalculator(new IntimaciesBonusPointCalculator(this));
  }

  @Override
  public void initializeListening(final ChangeAnnouncer announcer) {
    addModelChangeListener(new UnspecifiedChangeListener(announcer));
    addModelChangeListener(new RemovableEntryChangeAdapter<Intimacy>(announcer));
    announcer.addListener(new FlavoredChangeListener() {
      @Override
      public void changeOccurred(ChangeFlavor flavor) {
        if (TraitChangeFlavor.changes(flavor, VirtueType.Conviction)) {
          for (Intimacy entry : getEntries()) {
            entry.resetCurrentValue();
          }
          fireModelChangedEvent();
        }
      }
    });
    announcer.addListener(new FlavoredChangeListener() {
      @Override
      public void changeOccurred(ChangeFlavor flavor) {
        if (TraitChangeFlavor.changes(flavor, VirtueType.Compassion, OtherTraitType.Willpower)) {
          fireModelChangedEvent();
          fireEntryChanged();
        }
      }
    });
  }

  @Override
  public int getFreeIntimacies() {
    return getCompassionValue() + getTrait(OtherTraitType.Willpower).getCurrentValue();
  }

  protected int getCompassionValue() {
    return getTrait(VirtueType.Compassion).getCurrentValue();
  }

  @Override
  public void setCurrentName(String name) {
    this.name = name;
    fireEntryChanged();
  }

  @Override
  public void addChangeListener(FlavoredChangeListener listener) {
    hero.getChangeAnnouncer().addListener(listener);
  }

  @Override
  protected Intimacy createEntry() {
    IntimacyImpl intimacy = new IntimacyImpl(hero, name, getInitialValue(), getConviction());
    intimacy.setComplete(!isCharacterExperienced());
    intimacy.addChangeListener(new ChangeListener() {
      @Override
      public void changeOccurred() {
        fireModelChangedEvent();
      }
    });
    return intimacy;
  }

  private void fireModelChangedEvent() {
    announcer.announce().changeOccurred();
  }

  @Override
  public int getCompletionValue() {
    return 5;
  }

  private ValuedTraitType getConviction() {
    return getTrait(VirtueType.Conviction);
  }

  private Trait getTrait(TraitType traitType) {
    return TraitModelFetcher.fetch(hero).getTrait(traitType);
  }

  @Override
  public int getIntimaciesLimit() {
    return getCompassionValue() + getTrait(OtherTraitType.Willpower).getCurrentValue();
  }

  private Integer getInitialValue() {
    if (isCharacterExperienced()) {
      return 0;
    }
    return getConviction().getCurrentValue();
  }

  @Override
  protected boolean isEntryAllowed() {
    return getEntries().size() < getIntimaciesLimit() && !Strings.isNullOrEmpty(name);
  }

  @Override
  public void addModelChangeListener(ChangeListener listener) {
    announcer.addListener(listener);
  }

  @Override
  public boolean isCharacterExperienced() {
    return ExperienceModelFetcher.fetch(hero).isExperienced();
  }
}