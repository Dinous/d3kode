/**.
 * 
 */
package org.liris.mTrace.actions.json;

import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

/**.
 * @author Dino
 * @27 avr. 2011
 */
public abstract class JsonTableAbstractAction implements SessionAware {

  private static final long   serialVersionUID = 5078264277068533593L;

  private Integer             rows             = 0;
  private Integer             page             = 0;
  private Integer             total            = 0;
  private Integer             record           = 0;
  private String              sord;
  private String              sidx;
  private String              searchField;
  private String              searchString;
  private String              searchOper;
  private boolean             loadonce         = false;
  private Map<String, Object> session;
  
  public abstract String execute();

  public String getJSON(){
    return execute();
  }

  /**
   * @return how many rows we want to have into the grid
   */
  public Integer getRows(){
    return rows;
  }

  /**
   * @param rows
   *          how many rows we want to have into the grid
   */
  public void setRows(Integer rows){
    this.rows = rows;
  }

  /**
   * @return current page of the query
   */
  public Integer getPage(){
    return page;
  }

  /**
   * @param page
   *          current page of the query
   */
  public void setPage(Integer page){
    this.page = page;
  }

  /**
   * @return total pages for the query
   */
  public Integer getTotal(){
    return total;
  }

  /**
   * @param total
   *          total pages for the query
   */
  public void setTotal(Integer total){
    this.total = total;
  }

  /**
   * @return total number of records for the query. e.g. select count(*) from
   *         table
   */
  public Integer getRecord(){
    return record;
  }

  /**
   * @param record
   *          total number of records for the query. e.g. select count(*) from
   *          table
   */
  public void setRecord(Integer record){

    this.record = record;

    if (this.record > 0 && this.rows > 0){
      this.total = (int) Math.ceil((double) this.record / (double) this.rows);
    }else{
      this.total = 0;
    }
  }

  /**
   * @return sorting order
   */
  public String getSord(){
    return sord;
  }

  /**
   * @param sord
   *          sorting order
   */
  public void setSord(String sord){
    this.sord = sord;
  }

  /**
   * @return get index row - i.e. user click to sort.
   */
  public String getSidx(){
    return sidx;
  }

  /**
   * @param sidx
   *          get index row - i.e. user click to sort.
   */
  public void setSidx(String sidx){
    this.sidx = sidx;
  }

  public void setSearchField(String searchField){
    this.searchField = searchField;
  }

  public void setSearchString(String searchString){
    this.searchString = searchString;
  }

  public void setSearchOper(String searchOper){
    this.searchOper = searchOper;
  }

  public void setLoadonce(boolean loadonce){
    this.loadonce = loadonce;
  }

  public void setSession(Map<String, Object> session){
    this.session = session;
  }

public String getSearchField() {
    return searchField;
}

public String getSearchString() {
    return searchString;
}

public String getSearchOper() {
    return searchOper;
}

public boolean isLoadonce() {
    return loadonce;
}

public Map<String, Object> getSession() {
    return session;
}

}
