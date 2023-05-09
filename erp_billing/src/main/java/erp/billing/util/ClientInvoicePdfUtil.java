package erp.billing.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

import erp.billing.dto.response.ClientInvoiceFetchResponse;
import erp.billing.dto.response.ClientInvoiceProductFetchResponse;
import erp.billing.entity.Site;

public class ClientInvoicePdfUtil {

	static SimpleDateFormat DateFor = new SimpleDateFormat("dd-MM-yyyy hh:mm aa");
	static SimpleDateFormat DateFor1 = new SimpleDateFormat("dd-MM-yyyy");
	private static Font catFont = new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD);
	private static Font catFont10 = new Font(Font.FontFamily.HELVETICA, 8, Font.NORMAL);
	private static Font spacingFont = new Font(Font.FontFamily.HELVETICA, 0.01f, Font.BOLD);
	private static Font companyFont1 = new Font(FontFamily.HELVETICA, 15, Font.BOLD);
	private static Font redFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.RED);
	private static float widthPercentage = 95f;

	public static Document addContent(Document document, ClientInvoiceFetchResponse clientInvoiceFetchResponse,
			Site site) throws DocumentException, IOException {
		PdfPTable table = new PdfPTable(new float[] { 500f, 500f });
		table.setWidthPercentage(widthPercentage);

		try {

			PdfPCell cell = new PdfPCell(new Paragraph(
					"\n\n" + site.getGstNo() + "\n" + site.getCompany().getName() + "\n\n\n", companyFont1));
			cell.disableBorderSide(Rectangle.RIGHT);
			table.addCell(cell);
			cell.setFixedHeight(50f);
			PdfPCell cell2 = new PdfPCell();
			if (clientInvoiceFetchResponse.getClientInvoiceIrnInfo() != null) {
				Image qrCode = Image.getInstance(
						getQRCodeImage(clientInvoiceFetchResponse.getClientInvoiceIrnInfo().getSignedQRCode(), 20, 20));
				qrCode.setBorderWidthRight(0.5f);
				qrCode.setBorderWidthTop(0.5f);
				qrCode.setBorderWidthBottom(0.5f);
				cell2.setImage(qrCode);
			}
			cell2.disableBorderSide(Rectangle.LEFT);
			cell2.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell2.setFixedHeight(50f);

			table.addCell(cell2);
			document.add(table);
		} catch (Exception e) {
			e.printStackTrace();
		}

//		e-Invoice Detail Table Info
		PdfPTable eInvoiceTable = new PdfPTable(new float[] { 15f, 80f, 20f, 40f, 25f, 40f });
		eInvoiceTable.setWidthPercentage(widthPercentage);
		PdfPCell eInvoiceDetail = new PdfPCell(new Paragraph("1.e-Invoice Details", catFont));
		eInvoiceDetail.disableBorderSide(Rectangle.TOP);
		PdfPCell status = new PdfPCell(new Paragraph("CANCELLED", redFont));
		status.disableBorderSide(Rectangle.TOP);
		status.disableBorderSide(Rectangle.LEFT);
		if (clientInvoiceFetchResponse.getClientInvoiceIrnInfo() != null
				&& clientInvoiceFetchResponse.getClientInvoiceIrnInfo().getCancelDate() != null) {
			eInvoiceDetail.setColspan(5);
			eInvoiceDetail.disableBorderSide(Rectangle.RIGHT);
			eInvoiceTable.addCell(eInvoiceDetail);
			eInvoiceTable.addCell(status);
		} else {
			eInvoiceDetail.setColspan(6);
			eInvoiceTable.addCell(eInvoiceDetail);
		}

		PdfPCell irn = new PdfPCell(new Phrase("IRN:", catFont));
		irn.disableBorderSide(Rectangle.RIGHT);
		irn.disableBorderSide(Rectangle.BOTTOM);
		PdfPCell cell = new PdfPCell(new Paragraph(clientInvoiceFetchResponse.getClientInvoiceIrnInfo() != null
				? clientInvoiceFetchResponse.getClientInvoiceIrnInfo().getIrn()
				: "NA", catFont10));
		cell.disableBorderSide(Rectangle.LEFT);
		cell.disableBorderSide(Rectangle.RIGHT);
		cell.disableBorderSide(Rectangle.BOTTOM);
		PdfPCell ackNo = new PdfPCell(new Phrase("Ack.No:", catFont));
		ackNo.disableBorderSide(Rectangle.LEFT);
		ackNo.disableBorderSide(Rectangle.RIGHT);
		ackNo.disableBorderSide(Rectangle.BOTTOM);
		PdfPCell cell2 = new PdfPCell(new Paragraph(clientInvoiceFetchResponse.getClientInvoiceIrnInfo() != null
				? clientInvoiceFetchResponse.getClientInvoiceIrnInfo().getAckNo()
				: "NA", catFont10));
		cell2.disableBorderSide(Rectangle.LEFT);
		cell2.disableBorderSide(Rectangle.RIGHT);
		cell2.disableBorderSide(Rectangle.BOTTOM);
		PdfPCell ackDate = new PdfPCell(new Phrase("Ack.Date:", catFont));
		ackDate.disableBorderSide(Rectangle.LEFT);
		ackDate.disableBorderSide(Rectangle.RIGHT);
		ackDate.disableBorderSide(Rectangle.BOTTOM);
		String stringDate = "";
		if (clientInvoiceFetchResponse.getClientInvoiceIrnInfo() != null
				&& clientInvoiceFetchResponse.getClientInvoiceIrnInfo().getAckDt() != null) {
			stringDate = DateFor.format(clientInvoiceFetchResponse.getClientInvoiceIrnInfo().getAckDt());
		}
		PdfPCell cell3 = new PdfPCell(new Paragraph(!stringDate.isEmpty() ? stringDate : "NA", catFont10));
		cell3.disableBorderSide(Rectangle.LEFT);
		cell3.disableBorderSide(Rectangle.BOTTOM);

		eInvoiceTable.addCell(irn);
		eInvoiceTable.addCell(cell);
		eInvoiceTable.addCell(ackNo);
		eInvoiceTable.addCell(cell2);
		eInvoiceTable.addCell(ackDate);
		eInvoiceTable.addCell(cell3);

		if (clientInvoiceFetchResponse.getClientInvoiceIrnInfo() != null
				&& clientInvoiceFetchResponse.getClientInvoiceIrnInfo().getCancelDate() != null) {
			String stringDate2 = "";
			stringDate2 = DateFor.format(clientInvoiceFetchResponse.getClientInvoiceIrnInfo().getCancelDate());
			PdfPCell cancelDate = new PdfPCell(new Phrase("Cancel Date :", catFont));
			cancelDate.disableBorderSide(Rectangle.RIGHT);
			cancelDate.disableBorderSide(Rectangle.LEFT);
			cancelDate.disableBorderSide(Rectangle.TOP);
			cancelDate.disableBorderSide(Rectangle.BOTTOM);
			PdfPCell cell4 = new PdfPCell(new Phrase(!stringDate2.isEmpty() ? stringDate2 : "NA", catFont10));
			cell4.disableBorderSide(Rectangle.LEFT);
			cell4.disableBorderSide(Rectangle.TOP);
			cell4.disableBorderSide(Rectangle.BOTTOM);

			PdfPCell cancelReason = new PdfPCell(new Phrase("Cancel Reason:", catFont));
			cancelReason.disableBorderSide(Rectangle.RIGHT);
			cancelReason.disableBorderSide(Rectangle.LEFT);
			cancelReason.disableBorderSide(Rectangle.TOP);
			cancelReason.disableBorderSide(Rectangle.BOTTOM);

			PdfPCell cell5 = new PdfPCell(
					new Phrase(clientInvoiceFetchResponse.getClientInvoiceIrnInfo().getCancelReason() != null
							? clientInvoiceFetchResponse.getClientInvoiceIrnInfo().getCancelReason()
							: "NA", catFont10));
			cell5.disableBorderSide(Rectangle.RIGHT);
			cell5.disableBorderSide(Rectangle.LEFT);
			cell5.disableBorderSide(Rectangle.TOP);
			cell5.disableBorderSide(Rectangle.BOTTOM);

			PdfPCell cancelRemark = new PdfPCell(new Phrase("Cancel Remark:", catFont));
			cancelRemark.disableBorderSide(Rectangle.RIGHT);
			cancelRemark.disableBorderSide(Rectangle.BOTTOM);
			cancelRemark.disableBorderSide(Rectangle.TOP);
			PdfPCell cell6 = new PdfPCell(
					new Paragraph(clientInvoiceFetchResponse.getClientInvoiceIrnInfo().getCancelRemark() != null
							? clientInvoiceFetchResponse.getClientInvoiceIrnInfo().getCancelRemark()
							: "NA", catFont10));
			cell6.disableBorderSide(Rectangle.RIGHT);
			cell6.disableBorderSide(Rectangle.LEFT);
			cell6.disableBorderSide(Rectangle.TOP);
			cell6.disableBorderSide(Rectangle.BOTTOM);

			eInvoiceTable.addCell(cancelRemark);
			eInvoiceTable.addCell(cell6);
			eInvoiceTable.addCell(cancelReason);
			eInvoiceTable.addCell(cell5);
			eInvoiceTable.addCell(cancelDate);
			eInvoiceTable.addCell(cell4);
		}

		document.add(eInvoiceTable);

//		Transaction Detail Table Info
		PdfPTable transactionTable = new PdfPTable(new float[] { 25f, 10f, 22f, 15f, 33f, 10f });
		transactionTable.setWidthPercentage(widthPercentage);
		PdfPCell spacingCell1 = new PdfPCell(new Paragraph("\b\b", spacingFont));
		spacingCell1.setColspan(6);
		PdfPCell transactionDetail = new PdfPCell(new Paragraph("2.Transaction Details", catFont));
		transactionDetail.setColspan(6);
		transactionDetail.disableBorderSide(Rectangle.TOP);
		PdfPCell supplyTypeCode = new PdfPCell(new Phrase("Supply Type Code:", catFont));
		supplyTypeCode.disableBorderSide(Rectangle.RIGHT);
		PdfPCell subHeadingCell4 = new PdfPCell(
				new Paragraph(clientInvoiceFetchResponse.getSupplyType().getName(), catFont10));
		subHeadingCell4.disableBorderSide(Rectangle.LEFT);
		subHeadingCell4.disableBorderSide(Rectangle.RIGHT);
		PdfPCell documentNo = new PdfPCell(new Phrase("Document No:", catFont));
		documentNo.disableBorderSide(Rectangle.LEFT);
		documentNo.disableBorderSide(Rectangle.RIGHT);
		PdfPCell subHeadingCell5 = new PdfPCell(new Paragraph(clientInvoiceFetchResponse.getInvoiceNo(), catFont10));
		subHeadingCell5.disableBorderSide(Rectangle.LEFT);
		subHeadingCell5.disableBorderSide(Rectangle.RIGHT);
		PdfPCell igst = new PdfPCell(
				new Phrase("IGST applicable despite Supplier and Recipient located in same State:", catFont));
		igst.disableBorderSide(Rectangle.LEFT);
		igst.disableBorderSide(Rectangle.RIGHT);
		PdfPCell subHeadingCell6 = new PdfPCell(
				new Paragraph(clientInvoiceFetchResponse.getIsIgst() ? "Yes" : "No", catFont10));
		subHeadingCell6.disableBorderSide(Rectangle.LEFT);
		PdfPCell pos = new PdfPCell(new Phrase("Place of Supply:", catFont));
		pos.disableBorderSide(Rectangle.RIGHT);
		pos.disableBorderSide(Rectangle.TOP);
		pos.disableBorderSide(Rectangle.BOTTOM);
		PdfPCell subHeadingCell7 = new PdfPCell(
				new Phrase(clientInvoiceFetchResponse.getShippingStateName(), catFont10));
		subHeadingCell7.setColspan(5);
		subHeadingCell7.disableBorderSide(Rectangle.LEFT);
		subHeadingCell7.disableBorderSide(Rectangle.TOP);
		subHeadingCell7.disableBorderSide(Rectangle.BOTTOM);
		PdfPCell docType = new PdfPCell(new Phrase("Document Type:", catFont));
		docType.disableBorderSide(Rectangle.RIGHT);
		PdfPCell subHeadingCell8 = new PdfPCell(
				new Phrase(clientInvoiceFetchResponse.getInvoiceType().getName(), catFont10));
		subHeadingCell8.disableBorderSide(Rectangle.LEFT);
		subHeadingCell8.disableBorderSide(Rectangle.RIGHT);
		String stringDate1 = "";
		if (clientInvoiceFetchResponse.getInvoiceDate() != null) {
			stringDate1 = DateFor1.format(clientInvoiceFetchResponse.getInvoiceDate());
		}
		PdfPCell docDate = new PdfPCell(new Phrase("Document Date:", catFont));
		docDate.disableBorderSide(Rectangle.LEFT);
		docDate.disableBorderSide(Rectangle.RIGHT);
		PdfPCell subHeadingCell9 = new PdfPCell(new Paragraph(!stringDate1.isEmpty() ? stringDate1 : "NA", catFont10));
		subHeadingCell9.disableBorderSide(Rectangle.LEFT);
		subHeadingCell9.setColspan(3);

		transactionTable.addCell(spacingCell1);
		transactionTable.addCell(transactionDetail);
		transactionTable.addCell(supplyTypeCode);
		transactionTable.addCell(subHeadingCell4);
		transactionTable.addCell(documentNo);
		transactionTable.addCell(subHeadingCell5);
		transactionTable.addCell(igst);
		transactionTable.addCell(subHeadingCell6);
		transactionTable.addCell(pos);
		transactionTable.addCell(subHeadingCell7);
		transactionTable.addCell(docType);
		transactionTable.addCell(subHeadingCell8);
		transactionTable.addCell(docDate);
		transactionTable.addCell(subHeadingCell9);
		document.add(transactionTable);

//		Party Detail Table Info
		PdfPTable partyTable = new PdfPTable(new float[] { 80f, 80f });
		partyTable.setWidthPercentage(widthPercentage);
		PdfPCell spacingCell2 = new PdfPCell(new Paragraph("\b\b", spacingFont));
		spacingCell2.setColspan(2);
		spacingCell1.disableBorderSide(Rectangle.TOP);
		PdfPCell partyDetail = new PdfPCell(new Phrase("3.Party Details", catFont));
		partyDetail.setColspan(2);
		partyDetail.disableBorderSide(Rectangle.TOP);
		partyDetail.disableBorderSide(Rectangle.BOTTOM);
		PdfPCell supplier = new PdfPCell(new Phrase("Supplier", catFont));
		supplier.disableBorderSide(Rectangle.BOTTOM);
		PdfPCell recipient = new PdfPCell(new Phrase("Recipient", catFont));
		recipient.disableBorderSide(Rectangle.BOTTOM);
		PdfPCell supplierGstin = new PdfPCell(new Phrase("GSTIN:" + site.getGstNo(), catFont10));
		supplierGstin.disableBorderSide(Rectangle.TOP);
		supplierGstin.disableBorderSide(Rectangle.BOTTOM);
		PdfPCell recipientGstin = new PdfPCell(new Phrase("GSTIN:" + clientInvoiceFetchResponse.getGstNo(), catFont10));
		recipientGstin.disableBorderSide(Rectangle.TOP);
		recipientGstin.disableBorderSide(Rectangle.BOTTOM);
		PdfPCell supplierName = new PdfPCell(new Paragraph(site.getName(), catFont10));
		supplierName.disableBorderSide(Rectangle.TOP);
		supplierName.disableBorderSide(Rectangle.BOTTOM);
		PdfPCell recipientName = new PdfPCell(new Paragraph(clientInvoiceFetchResponse.getCiuOfficeName(), catFont10));
		recipientName.disableBorderSide(Rectangle.TOP);
		recipientName.disableBorderSide(Rectangle.BOTTOM);
		PdfPCell supplierAdd = new PdfPCell(new Paragraph(site.getAddress(), catFont10));
		supplierAdd.disableBorderSide(Rectangle.TOP);
		supplierAdd.disableBorderSide(Rectangle.BOTTOM);
		PdfPCell recipientAdd = new PdfPCell(new Paragraph(clientInvoiceFetchResponse.getShippingAddress(), catFont10));
		recipientAdd.disableBorderSide(Rectangle.TOP);
		recipientAdd.disableBorderSide(Rectangle.BOTTOM);
		PdfPCell supplierCity = new PdfPCell(new Paragraph(site.getCity().getName(), catFont10));
		supplierCity.disableBorderSide(Rectangle.TOP);
		supplierCity.disableBorderSide(Rectangle.BOTTOM);
		PdfPCell recipientCity = new PdfPCell(
				new Paragraph(clientInvoiceFetchResponse.getShippingCityName(), catFont10));
		recipientCity.disableBorderSide(Rectangle.TOP);
		recipientCity.disableBorderSide(Rectangle.BOTTOM);
		PdfPCell supplierZipState = new PdfPCell(
				new Paragraph(site.getZipCode().getCode() + "\b\b" + site.getState().getName(), catFont10));
		supplierZipState.disableBorderSide(Rectangle.TOP);
		supplierZipState.disableBorderSide(Rectangle.BOTTOM);
		PdfPCell recipientZipState = new PdfPCell(new Paragraph(clientInvoiceFetchResponse.getShippingZipCode() + "\b\b"
				+ clientInvoiceFetchResponse.getShippingStateName(), catFont10));
		recipientZipState.disableBorderSide(Rectangle.BOTTOM);
		recipientZipState.disableBorderSide(Rectangle.TOP);

		partyTable.addCell(spacingCell2);
		partyTable.addCell(partyDetail);
		partyTable.addCell(supplier);
		partyTable.addCell(recipient);
		partyTable.addCell(supplierGstin);
		partyTable.addCell(recipientGstin);
		partyTable.addCell(supplierName);
		partyTable.addCell(recipientName);
		partyTable.addCell(supplierAdd);
		partyTable.addCell(recipientAdd);
		partyTable.addCell(supplierCity);
		partyTable.addCell(recipientCity);
		partyTable.addCell(supplierZipState);
		partyTable.addCell(recipientZipState);
		document.add(partyTable);

//		Details of Goods/Services Table Info
		PdfPTable serviceTable = new PdfPTable(new float[] { 15f, 20f, 15f, 20f, 10f, 20f, 20f, 20f, 20f, 20f, 20f });
		serviceTable.setWidthPercentage(widthPercentage);
		PdfPCell spacingCell3 = new PdfPCell(new Paragraph("\b\b", spacingFont));
		spacingCell3.setColspan(11);
		spacingCell3.disableBorderSide(Rectangle.TOP);
		PdfPCell serviceDetail = new PdfPCell(new Paragraph("4.Details of Goods/Services", catFont));
		serviceDetail.setColspan(11);
		PdfPCell subHeadingCell10 = new PdfPCell(new Phrase("SLNo", catFont));
		subHeadingCell10.setHorizontalAlignment(Element.ALIGN_CENTER);
		PdfPCell subHeadingCell11 = new PdfPCell(new Phrase("Item Description", catFont));
		subHeadingCell11.setHorizontalAlignment(Element.ALIGN_CENTER);
		PdfPCell subHeadingCell12 = new PdfPCell(new Phrase("HSN Code", catFont));
		subHeadingCell12.setHorizontalAlignment(Element.ALIGN_CENTER);
		PdfPCell subHeadingCell13 = new PdfPCell(new Phrase("Quantity", catFont));
		subHeadingCell13.setHorizontalAlignment(Element.ALIGN_CENTER);
		PdfPCell subHeadingCell14 = new PdfPCell(new Phrase("Unit", catFont));
		subHeadingCell14.setHorizontalAlignment(Element.ALIGN_CENTER);
		PdfPCell subHeadingCell15 = new PdfPCell(new Phrase("Unit Price(Rs)", catFont));
		subHeadingCell15.setHorizontalAlignment(Element.ALIGN_CENTER);
		PdfPCell subHeadingCell16 = new PdfPCell(new Phrase("Discount(Rs)", catFont));
		subHeadingCell16.setHorizontalAlignment(Element.ALIGN_CENTER);
		PdfPCell subHeadingCell17 = new PdfPCell(new Phrase("Taxable Amount(Rs)", catFont));
		subHeadingCell17.setHorizontalAlignment(Element.ALIGN_CENTER);
		PdfPCell subHeadingCell18 = new PdfPCell(new Phrase("Tax Rate(GST+Cess|StateCess+CessNon.Advol)", catFont));
		subHeadingCell18.setHorizontalAlignment(Element.ALIGN_CENTER);
		PdfPCell subHeadingCell19 = new PdfPCell(new Phrase("Other charges(Rs)", catFont));
		subHeadingCell19.setHorizontalAlignment(Element.ALIGN_CENTER);
		PdfPCell subHeadingCell20 = new PdfPCell(new Phrase("Total", catFont));
		subHeadingCell20.setHorizontalAlignment(Element.ALIGN_CENTER);

		serviceTable.addCell(spacingCell3);
		serviceTable.addCell(serviceDetail);
		serviceTable.addCell(subHeadingCell10);
		serviceTable.addCell(subHeadingCell11);
		serviceTable.addCell(subHeadingCell12);
		serviceTable.addCell(subHeadingCell13);
		serviceTable.addCell(subHeadingCell14);
		serviceTable.addCell(subHeadingCell15);
		serviceTable.addCell(subHeadingCell16);
		serviceTable.addCell(subHeadingCell17);
		serviceTable.addCell(subHeadingCell18);
		serviceTable.addCell(subHeadingCell19);
		serviceTable.addCell(subHeadingCell20);

		Double totaltaxableAmt = 0.0;
		Double totalCgstAmt = 0.0;
		Double totalsgstAmt = 0.0;
		Double totalIgstAmt = 0.0;

		int slNoCount = 1;
		for (ClientInvoiceProductFetchResponse product : clientInvoiceFetchResponse.getProducts()) {
			PdfPCell slNo = new PdfPCell(new Paragraph(String.valueOf(slNoCount), catFont10));
			PdfPCell productName = new PdfPCell(new Phrase(product.getName(), catFont10));
			PdfPCell hsnCode = new PdfPCell(new Phrase(product.getHsnCode(), catFont10));
			hsnCode.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell quantity = new PdfPCell(new Phrase(product.getQuantity().toString(), catFont10));
			PdfPCell unitName = new PdfPCell(new Phrase(product.getUnitName(), catFont10));
			unitName.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell unitPrice = new PdfPCell(new Phrase(
					String.valueOf(String.format("%.2f", product.getAmount() / product.getQuantity())), catFont10));
			unitPrice.setHorizontalAlignment(Element.ALIGN_RIGHT);
			PdfPCell discount = new PdfPCell(new Phrase("0", catFont10));
			discount.setHorizontalAlignment(Element.ALIGN_RIGHT);
			PdfPCell taxableAmt = new PdfPCell(new Phrase(String.valueOf(product.getAmount()), catFont10));
			taxableAmt.setHorizontalAlignment(Element.ALIGN_RIGHT);
			PdfPCell taxRate = new PdfPCell(new Paragraph(
					String.valueOf(product.getIsIgst() ? product.getApplicableIgstPercentage()
							: product.getApplicableCgstPercentage() + product.getApplicableSgstPercentage()),
					catFont10));
			taxRate.setHorizontalAlignment(Element.ALIGN_RIGHT);
			PdfPCell otherCharges = new PdfPCell(new Phrase("0", catFont10));
			otherCharges.setHorizontalAlignment(Element.ALIGN_RIGHT);
			PdfPCell total = new PdfPCell(new Phrase(String.valueOf(product.getIsIgst()
					? product.getApplicableIgstAmount() + product.getAmount()
					: product.getApplicableCgstAmount() + product.getApplicableSgstAmount() + product.getAmount()),
					catFont10));
			total.setHorizontalAlignment(Element.ALIGN_RIGHT);

			totaltaxableAmt = totaltaxableAmt + product.getAmount();
			if (!product.getIsIgst()) {
				totalCgstAmt = totalCgstAmt + product.getApplicableCgstAmount();
				totalsgstAmt = totalsgstAmt + product.getApplicableSgstAmount();
			} else {
				totalIgstAmt = totalIgstAmt + product.getApplicableIgstAmount();
			}
			slNoCount++;
			serviceTable.addCell(slNo);
			serviceTable.addCell(productName);
			serviceTable.addCell(hsnCode);
			serviceTable.addCell(quantity);
			serviceTable.addCell(unitName);
			serviceTable.addCell(unitPrice);
			serviceTable.addCell(discount);
			serviceTable.addCell(taxableAmt);
			serviceTable.addCell(taxRate);
			serviceTable.addCell(otherCharges);
			serviceTable.addCell(total);
		}
		document.add(serviceTable);

//		All Amount Value Table
		PdfPTable taxTable = new PdfPTable(new float[] { 22f, 17f, 17f, 17f, 15f, 15f, 15f, 12f, 15f, 22f });
		taxTable.setWidthPercentage(widthPercentage);
		PdfPCell spaceingCell = new PdfPCell(new Phrase("\b\b", spacingFont));
		spaceingCell.setColspan(11);
		spaceingCell.disableBorderSide(Rectangle.TOP);
		PdfPCell subHeadingCell32 = new PdfPCell(new Phrase("Taxable Amt", catFont));
		subHeadingCell32.setHorizontalAlignment(Element.ALIGN_CENTER);
		PdfPCell subHeadingCell33 = new PdfPCell(new Phrase("CGST Amt", catFont));
		subHeadingCell33.setHorizontalAlignment(Element.ALIGN_CENTER);
		PdfPCell subHeadingCell34 = new PdfPCell(new Phrase("SGST Amt", catFont));
		subHeadingCell34.setHorizontalAlignment(Element.ALIGN_CENTER);
		PdfPCell subHeadingCell35 = new PdfPCell(new Phrase("IGST Amt", catFont));
		subHeadingCell35.setHorizontalAlignment(Element.ALIGN_CENTER);
		PdfPCell subHeadingCell36 = new PdfPCell(new Phrase("CESS Amt", catFont));
		subHeadingCell36.setHorizontalAlignment(Element.ALIGN_CENTER);
		PdfPCell subHeadingCell37 = new PdfPCell(new Phrase("State CESS Amt", catFont));
		subHeadingCell37.setHorizontalAlignment(Element.ALIGN_CENTER);
		PdfPCell subHeadingCell38 = new PdfPCell(new Phrase("Discount", catFont));
		subHeadingCell38.setHorizontalAlignment(Element.ALIGN_CENTER);
		PdfPCell subHeadingCell39 = new PdfPCell(new Phrase("Other charges", catFont));
		subHeadingCell39.setHorizontalAlignment(Element.ALIGN_CENTER);
		PdfPCell subHeadingCell40 = new PdfPCell(new Phrase("Round off Amt", catFont));
		subHeadingCell40.setHorizontalAlignment(Element.ALIGN_CENTER);
		PdfPCell subHeadingCell41 = new PdfPCell(new Phrase("Total Inv.Amt", catFont));
		subHeadingCell41.setHorizontalAlignment(Element.ALIGN_CENTER);

		PdfPCell taxableAmt = new PdfPCell(
				new Phrase(String.valueOf(String.format("%.2f", totaltaxableAmt)), catFont10));
		taxableAmt.setHorizontalAlignment(Element.ALIGN_RIGHT);
		PdfPCell cgstAmt = new PdfPCell(new Phrase(String.valueOf(String.format("%.2f", totalCgstAmt)), catFont10));
		cgstAmt.setHorizontalAlignment(Element.ALIGN_RIGHT);
		PdfPCell sgstAmt = new PdfPCell(new Phrase(String.valueOf(String.format("%.2f", totalsgstAmt)), catFont10));
		sgstAmt.setHorizontalAlignment(Element.ALIGN_RIGHT);
		PdfPCell igstAmt = new PdfPCell(new Phrase(String.valueOf(String.format("%.2f", totalIgstAmt)), catFont10));
		igstAmt.setHorizontalAlignment(Element.ALIGN_RIGHT);
		PdfPCell cessAmt = new PdfPCell(new Phrase("0", catFont10));
		cessAmt.setHorizontalAlignment(Element.ALIGN_RIGHT);
		PdfPCell stateCessAmt = new PdfPCell(new Phrase("0", catFont10));
		stateCessAmt.setHorizontalAlignment(Element.ALIGN_RIGHT);
		PdfPCell discount = new PdfPCell(new Phrase("0", catFont10));
		discount.setHorizontalAlignment(Element.ALIGN_RIGHT);
		PdfPCell otherCharges = new PdfPCell(new Phrase("0", catFont10));
		otherCharges.setHorizontalAlignment(Element.ALIGN_RIGHT);
		PdfPCell roundOffAmt = new PdfPCell(new Phrase("0", catFont10));
		roundOffAmt.setHorizontalAlignment(Element.ALIGN_RIGHT);
		PdfPCell totalInvAmt = new PdfPCell(new Phrase(
				String.valueOf(String.format("%.2f", totaltaxableAmt + totalCgstAmt + totalsgstAmt + totalIgstAmt)),
				catFont10));
		totalInvAmt.setHorizontalAlignment(Element.ALIGN_RIGHT);

		taxTable.addCell(spaceingCell);
		taxTable.addCell(subHeadingCell32);
		taxTable.addCell(subHeadingCell33);
		taxTable.addCell(subHeadingCell34);
		taxTable.addCell(subHeadingCell35);
		taxTable.addCell(subHeadingCell36);
		taxTable.addCell(subHeadingCell37);
		taxTable.addCell(subHeadingCell38);
		taxTable.addCell(subHeadingCell39);
		taxTable.addCell(subHeadingCell40);
		taxTable.addCell(subHeadingCell41);

		taxTable.addCell(taxableAmt);
		taxTable.addCell(cgstAmt);
		taxTable.addCell(sgstAmt);
		taxTable.addCell(igstAmt);
		taxTable.addCell(cessAmt);
		taxTable.addCell(stateCessAmt);
		taxTable.addCell(discount);
		taxTable.addCell(otherCharges);
		taxTable.addCell(roundOffAmt);
		taxTable.addCell(totalInvAmt);

		document.add(taxTable);
		return document;

	}

	public static byte[] getQRCodeImage(String text, int width, int height) throws WriterException, IOException {
		QRCodeWriter qrCodeWriter = new QRCodeWriter();
		BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);
		ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
		MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
		byte[] pngData = pngOutputStream.toByteArray();
		return pngData;
	}

}
