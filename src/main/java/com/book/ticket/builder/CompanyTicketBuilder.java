package com.book.ticket.builder;


import com.book.ticket.product.CompanyTicket;

public class CompanyTicketBuilder extends TicketBuilder<CompanyTicket> {

    //创建一个新的 companyTicket 对象
    private CompanyTicket companyTicket = new CompanyTicket();
    //对于CompanyTicket，我们需要setCommonInfo
    @Override
    public void setCommonInfo(String title, String product, String content) {
        companyTicket.setTitle(title);
        companyTicket.setProduct(product);
        companyTicket.setContent(content);
    }
    //对于CompanyTicket，我们需要setTaxId
    @Override
    public void setTaxId(String taxId) {
        companyTicket.setTaxId(taxId);
    }
    //对于CompanyTicket，我们需要setBankInfo
    @Override
    public void setBankInfo(String bankInfo) {
        companyTicket.setBankInfo(bankInfo);
    }
    //返回 企业电子发票
    @Override
    public CompanyTicket buildTicket() {
        return companyTicket;
    }
}
