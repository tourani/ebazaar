package business.productsubsystem;

import java.time.LocalDate;
import java.util.List;

import middleware.exceptions.DatabaseException;
import business.exceptions.BackendException;
import business.externalinterfaces.*;
import business.util.TwoKeyHashMap;

public class ProductSubsystemFacade implements ProductSubsystem {
	public static Catalog createCatalog(int id, String name) {
		return new CatalogImpl(id, name);
	}
	public static Product createProduct(Catalog c,int id, String name, 
			LocalDate date, int numAvail, double price) {
		return new ProductImpl(c, name, date, numAvail, price);
	}
	public static Product createProduct(Catalog c, Integer pi, String pn, int qa, 
			double up, LocalDate md, String desc) {
		return new ProductImpl(c, pi, pn, qa, up, md, desc);
	}
	
	/** obtains product for a given product name */
    public Product getProductFromName(String prodName) throws BackendException {
    	try {
			DbClassProduct dbclass = new DbClassProduct();
			return dbclass.readProduct(getProductIdFromName(prodName));
		} catch(DatabaseException e) {
			throw new BackendException(e);
		}	
    }
    public Integer getProductIdFromName(String prodName) throws BackendException {
		try {
			DbClassProduct dbclass = new DbClassProduct();
			TwoKeyHashMap<Integer,String,Product> table = dbclass.readProductTable();
			return table.getFirstKey(prodName);
		} catch(DatabaseException e) {
			throw new BackendException(e);
		}
		
	}
    public Product getProductFromId(Integer prodId) throws BackendException {
		try {
			DbClassProduct dbclass = new DbClassProduct();
			return dbclass.readProduct(prodId);
		} catch(DatabaseException e) {
			throw new BackendException(e);
		}
	}
    
    public List<Catalog> getCatalogList() throws BackendException {
    	try {
			DbClassCatalogTypes dbClass = new DbClassCatalogTypes();
			return dbClass.getCatalogTypes().getCatalogs();
		} catch(DatabaseException e) {
			throw new BackendException(e);
		}
		
    }
    
    public List<Product> getProductList(Catalog catalog) throws BackendException {
    	try {
    		DbClassProduct dbclass = new DbClassProduct();
    		return dbclass.readProductList(catalog);
    	} catch(DatabaseException e) {
    		throw new BackendException(e);
    	}
    }
	
    public int readQuantityAvailable(Product product) {
		int quantity = 0;
		try {
			DbClassProduct dbclass = new DbClassProduct();
			quantity = dbclass.readProduct(product.getProductId()).getQuantityAvail();
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
			
		return quantity;
	}
	
	@Override
	public Catalog getCatalogFromName(String catName) throws BackendException {
		// stubbing
		try {
			DbClassCatalogTypes dbclass = new DbClassCatalogTypes();
			int i = dbclass.getCatalogTypes().getCatalogId(catName);
			return ProductSubsystemFacade.createCatalog(i, catName);
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
		throw new BackendException(e);
		}
		
	}
	@Override
	public void saveNewCatalog(Catalog cat) throws BackendException {
		// TODO Auto-generated method stub
		try {
		DbClassCatalog dbclass = new DbClassCatalog();
		dbclass.saveNewCatalog(cat);
		}
		catch(DatabaseException e) {
    		throw new BackendException(e);
    	}
	}
	@Override
//	public void saveNewProduct(Product product) throws BackendException {
//		// TODO Auto-generated method stub
//		try {
//		DbClassProduct dbclass = new DbClassProduct();
//		dbclass.saveNewProduct(product);
//		}
//		catch(DatabaseException e) {
//    		throw new BackendException(e);
//    	}
//	}
	public Integer saveNewProduct(Product product) throws BackendException {
		Integer productId = -1;
		
		DbClassProduct dbClass = new DbClassProduct();
		try {
			productId = dbClass.saveNewProduct(product, product.getCatalog());
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
		
		return productId;
	}
	@Override
	public void deleteProduct(Product product) throws BackendException {
		DbClassProduct dbClass = new DbClassProduct();
		try {
			dbClass.deleteProduct(product);
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
	}
//	@Override
//	public void deleteCatalog(Catalog catalog) throws BackendException {
//		// TODO Auto-generated method stub
//		try {
//		DbClassCatalogTypes dbClass = new DbClassCatalogTypes();
//		dbClass.getCatalogTypes().getCatalogs().remove(catalog);
//		}
//		catch(DatabaseException e) {
//    		throw new BackendException(e);
//		}
//	}
	
	@Override
	public void deleteCatalog(Catalog catalog) throws BackendException {
		// TODO Auto-generated method stub
		try {
		DbClassCatalog dbClass = new DbClassCatalog();
		dbClass.deleteCatalog(catalog);;
		}
		catch(DatabaseException e) {
    		throw new BackendException(e);
		}
	}
}
