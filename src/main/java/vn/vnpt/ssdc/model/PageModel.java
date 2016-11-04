package vn.vnpt.ssdc.model;

/**
 * Created by SSDC on 11/1/2016.
 */
public class PageModel {

    private int previous;
    private int next;

    private boolean isFistPage;
    private boolean isLastPage;

    private int currentPage;
    private int maxPage;

    private boolean hasPrevious;
    private boolean hasNext;

    public PageModel() {
    }


    public int getPrevious() {
        return previous;
    }

    public void setPrevious(int previous) {
        this.previous = previous;
    }

    public int getNext() {
        return next;
    }

    public void setNext(int next) {
        this.next = next;
    }

    public boolean isFistPage() {
        return isFistPage;
    }

    public void setFistPage(boolean fistPage) {
        isFistPage = fistPage;
    }

    public boolean isLastPage() {
        return isLastPage;
    }

    public void setLastPage(boolean lastPage) {
        isLastPage = lastPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getMaxPage() {
        return maxPage;
    }

    public void setMaxPage(int maxPage) {
        this.maxPage = maxPage;
    }

    public boolean isHasPrevious() {
        return hasPrevious;
    }

    public void setHasPrevious(boolean hasPrevious) {
        this.hasPrevious = hasPrevious;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

}
