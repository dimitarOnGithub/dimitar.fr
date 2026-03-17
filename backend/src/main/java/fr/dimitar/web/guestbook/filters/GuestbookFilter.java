package fr.dimitar.web.guestbook.filters;

public class GuestbookFilter {

    // Pagination
    private Integer page = 0;
    private Integer pageSize = 5;

    // Only approved entries
    private boolean approvedOnly = false;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public boolean isApprovedOnly() {
        return this.approvedOnly;
    }

    public void setApprovedOnly(boolean approvedOnly) {
        this.approvedOnly = approvedOnly;
    }

}
