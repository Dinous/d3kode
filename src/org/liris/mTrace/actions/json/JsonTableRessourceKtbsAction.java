package org.liris.mTrace.actions.json;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.liris.ktbs.domain.interfaces.IKtbsResource;
import org.liris.mTrace.actions.D3KODEAction;
import org.liris.mTrace.kTBS.SingleKtbs;
import org.liris.mTrace.tools.pojo.RessourceKtbsPojo;

@ParentPackage(value = "mTrace")
public class JsonTableRessourceKtbsAction extends D3KODEAction{
	private static final long   serialVersionUID = 5078264277068533593L;
	  

	  private List<RessourceKtbsPojo>      gridedittableKtbs;
	  private List<RessourceKtbsPojo>      ressources;
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

	  @Actions( {
		    @Action(value = "/jsontableResourceKtbs", results = {
		      @Result(name = "success", type = "json")
		    })
		  })
	  public String execute(){

		  List<IKtbsResource> ressourcesKtbs = SingleKtbs.getInstance().getResources(true);
		  ressources = new ArrayList<RessourceKtbsPojo>();
		  List<String> ktbsResourceUri = new ArrayList<String>();
	    for (IKtbsResource ktbsResource : ressourcesKtbs) {
			RessourceKtbsPojo ressourceKtbsPojo = new RessourceKtbsPojo(ktbsResource);
			ktbsResourceUri.add(ressourceKtbsPojo.getUri());
			ressources.add(ressourceKtbsPojo);
		}
	    getSession().put("resourceKtbs", ktbsResourceUri);

	    if (getSord() != null && getSord().equalsIgnoreCase("asc")){
	      //Collections.sort(ressources);
	    }
	    if (getSord() != null && getSord().equalsIgnoreCase("desc")){
	      //Collections.sort(ressources);
	      Collections.reverse(ressources);
	    }

	    setRecord(ressources.size());

	    int to = (getRows() * getPage());

	    if (to > getRecord()) to = getRecord();
	    
	    setGridedittableKtbs(ressources);
	    setTotal((int) Math.ceil((double) getRecord() / (double) getRows()));

	    return SUCCESS;
	  }

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
	   * @return an collection that contains the actual data
	   */
	  public List<RessourceKtbsPojo> getGridedittableKtbs(){
	    return gridedittableKtbs;
	  }

	  /**
	   * @param gridedittableKtbs
	   *          an collection that contains the actual data
	   */
	  public void setGridedittableKtbs(List<RessourceKtbsPojo> gridedittableKtbs){
	    this.gridedittableKtbs = gridedittableKtbs;
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
}
