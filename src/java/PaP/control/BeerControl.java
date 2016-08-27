package PaP.control;

import PaP.PaPException;
import PaP.model.Beer;
import PaP.model.ObjectModel;
import PaP.model.impl.ObjectModelImpl;
import PaP.persistence.Persistence;
import PaP.persistence.impl.DbUtils;
import PaP.persistence.impl.PersistenceImpl;
import java.sql.*;
import java.util.*;

public class BeerControl{
	private Connection conn = null;
	private ObjectModel objectModel = null;
	private Persistence persistence = null;
	private String error="Error Unknown";
	private boolean hasError = false;
	private LoginControl ctrl = new LoginControl();
	private void connect() throws PaPException{
		conn = DbUtils.connect();
		objectModel = new ObjectModelImpl();
		persistence = new PersistenceImpl(conn,objectModel);
		objectModel.setPersistence(persistence);
	}
	private void close(){
		try{
			conn.close();
		}catch(Exception e){
			System.err.println("Exception: "+e);
		}
	}

	public long attemptBeerCreate(Map<String,String[]> parameters, long userId){

		boolean created=true;
		try{
			connect();
			Beer beer = objectModel.createBeer();
                        beer.setCode(parameters.get("code")[0]);
			beer.setName(parameters.get("name")[1]);
                        beer.setBrand(parameters.get("brand")[2]);
                        beer.setType(parameters.get("type")[3]);
                        beer.setABV(Double.parseDouble(parameters.get("abv")[4]));
                        beer.setIBU(Integer.parseInt(parameters.get("ibu")[5]));
			beer.setDesc(parameters.get("description")[6]);
			
                        objectModel.storeBeer(beer);
			return beer.getId();	
				
		}catch(PaPException e){
			error = e.getMessage();
			hasError = true;
			return -1;
		}finally{
			close();
		}
	
	}
	
	
	public ArrayList<Beer> getBeerList(){
		try{
			this.connect();
			Beer modelBeer = this.objectModel.createBeer();
			Iterator<Beer> beers = this.objectModel.findBeer(modelBeer);
			ArrayList<Beer> beersMap = new ArrayList<Beer>();
			while(beers.hasNext()){				
				beersMap.add(beers.next());
			}
//			error = Integer.toString(categoriesMap.size());		
			return beersMap;	
		}catch(PaPException e){
			error = e.getMessage();
			hasError=true;
			return null;
		}finally{
			this.close();
		}
	}

	
	public void removeBeer(long beerId){
		try{
			this.connect();
			Beer beer = objectModel.createBeer();
			beer.setId(beerId);
			objectModel.deleteBeer(beer);
		}catch(PaPException e){
                        error = e.getMessage();
                        hasError=true;
                //        return null;
                }finally{
                        this.close();
                }

	}
	public String getError(){
		return error;
	}	
	public boolean hasError(){
		return hasError;
	}
}
