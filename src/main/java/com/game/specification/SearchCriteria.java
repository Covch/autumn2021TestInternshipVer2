package com.game.specification;


public class SearchCriteria {
    private String key, value, name;
    private SearchOperation searchOperation;

    public SearchCriteria(String name, String value) {
        this.name = name;
        this.value = value;
        switch (name) {
            case "name":
            case "title":
                this.searchOperation = SearchOperation.LIKE;
                this.key = name;
                break;
            case "banned":
            case "race":
            case "profession":
                this.searchOperation = SearchOperation.EQUALITY;
                this.key = name;
                break;
            case "minLevel":
                this.searchOperation = SearchOperation.GREATER_THAN;
                this.key = "level";
                break;
            case "maxLevel":
                this.searchOperation = SearchOperation.LESS_THAN;
                this.key = "level";
                break;
            case "before":
                this.searchOperation = SearchOperation.LESS_THAN_DATE;
                this.key = "birthday";
                break;
            case "after":
                this.searchOperation = SearchOperation.GREATER_THAN_DATE;
                this.key = "birthday";
                break;
            case "minExperience":
                this.searchOperation = SearchOperation.GREATER_THAN;
                this.key = "experience";
                break;
            case "maxExperience":
                this.searchOperation = SearchOperation.LESS_THAN;
                this.key = "experience";
                break;
            default:
                this.searchOperation = null;
                this.key = null;
        }
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public SearchOperation getSearchOperation() {
        return searchOperation;
    }

    public void setSearchOperation(SearchOperation searchOperation) {
        this.searchOperation = searchOperation;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
