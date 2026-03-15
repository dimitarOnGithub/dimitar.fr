package fr.dimitar.web.posts.filters;

import java.util.List;

public class PostsFilter {

    // Pagination
    private Integer page = 0;
    private Integer pageSize = 5;

    // Drafts
    private boolean draftsOnly = false;

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

    public boolean isDraftsOnly() {
        return draftsOnly;
    }

    public void setDraftsOnly(boolean draftsOnly) {
        this.draftsOnly = draftsOnly;
    }

}
