package com.book.ticket.builder;

import com.book.ticket.product.PersonalTicket;

public class PersonalTicketBuilder extends TicketBuilder<PersonalTicket> {
    //创建一个新的 personalTicket 对象
    private PersonalTicket personalTicket = new PersonalTicket();
    //对于PersonalTicket，我们仅需要setCommonInfo即可
    @Override
    public void setCommonInfo(String title, String product, String content) {
        personalTicket.setTitle(title);
        personalTicket.setProduct(product);
        personalTicket.setContent(content);
    }
    //返回 个人电子发票
    @Override
    public PersonalTicket buildTicket() {
        return personalTicket;
    }
}
