package net.sf.anathema.framework.reporting.pdf;

import net.sf.anathema.framework.reporting.Report;
import net.sf.anathema.framework.reporting.ReportException;
import net.sf.anathema.hero.model.Hero;

import java.io.OutputStream;

public abstract class AbstractPdfReport implements Report, PdfReport {

  @Override
  public final void print(Hero hero, OutputStream stream) throws ReportException {
    PdfReportPrinter printer = new PdfReportPrinter();
    printer.printReport(hero, this, stream);
  }
}
