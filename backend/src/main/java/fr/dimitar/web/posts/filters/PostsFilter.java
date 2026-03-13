package fr.dimitar.web.posts.filters;

import java.util.List;

public class PostsFilter {

    // Specific posts
    private List<Integer> id = List.of();

    // Pagination
    private Integer page = 0;
    private Integer pageSize = 5;

    // Drafts
    private boolean includeDrafts = false;

    public List<Integer> getId() {
        return id;
    }

    public void setId(List<Integer> id) {
        this.id = id;
    }

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

    public boolean isIncludeDrafts() {
        return includeDrafts;
    }

    public void setIncludeDrafts(boolean includeDrafts) {
        this.includeDrafts = includeDrafts;
    }

}
