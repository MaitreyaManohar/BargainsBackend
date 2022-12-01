# OopsProject
bunch of college students making an e commerce website for evals ;)

URL LIST FOR making requests:
GetMappings
-----------
  / for getting the list of all users.
  
  /getcart/{customerId} to get the list of carts which have the input customerId.
  
  /ewallet/{ownerId} to get the ewallet (along with all details of the ewallet) of the user whose id is specified.
  
  /items returns a list of all items with their details
  
  /items/{sellerId} returns a list of all items along with their details sold by a paricular user(manager)
  
  /get/{id} returns  the user details of the user whose id has been given
  
  /getany/{name} returns a list of all users along with details who have the name specified
  
  /getbyemail/{email} returns the user with specified email
  
  /orders/pastorders/{customerid} returns a list of all past orders of the customer with specified id
  
PostMappings
------------
   /adduser for adding a user to the database (request body should have all details of the user)
  
  /addtocart/{customerId} for adding an item to customer's cart whose id has been specified (request body should have details of the cart such as buyer_id,qty_bought etc)
  
  /ewallet/addewallet adds an ewallet to the ewallet table.(request body should have details like ownerId and balance.Note that the amount for balance should be >=1000 else the ewallet will be deleted)
  
  /items/additem for adding a new item to the items table(request body should have details like itemname sellername qty in stock in production or not etc)
   
PatchMappings
-------------
  /updatename/{id}/{name} changes the name of the user whose id has been specified to the name that has been specified
  
  /buyfromcart/{cartid} buys the item from cart which has the mentioned cart id.The amount is deducted from the ewallet of the user who is buying and the qty bought is  deducted from the corresponding item in the qty column in the items table.
  
  /ewallet/topup/{ownerId}/{amount} topups the ewallet of the user whose id is specified by the amount specified
  
  /items/statusupdate/{itemId}/{status} it changes the inProduction value for the specified item to the specified status
  
  /items/changeprice/{itemId}/{price} it changes the price of the specified item to the mentioned price
  
  /items/increaseqty/{itemId}/{qty} it increases the qty of the specified item by the qty specified
  
  /items/decreaseqty/{itemId}/{qty} it decreases the qty of the specified item by the qty specified
  
DeleteMapping
-------------
  /delete/{id} deletes the user

SOME BASIC SPRING INFO
----------------------
@Autowired is an annotation which is used when we need to specify some attribute getter or setter or a constructor as autowired.In this case the dependency is injected by the spring container into the attribute marked as autowired.

@controller is basically used as an interface between the frontend and the backend.It deals with client requests.It receives the request from the client and performs business logic on it and returns data as a JSON file.The controller class gets the request if the request is addressed to the url of the controller class.

@Id marks a field in a model class as the primary key

@RestController annotation is a convenience annotation that is itself annotated with @Controller and @ResponseBody. This annotation is applied to a class to mark it as a request handler

@RequestMapping Annotation is used to map HTTP requests to handler methods of MVC and REST controllers.

@GetMapping annotation is used for a function inside a class which is already a controller and has a request mapping annotation in it.An additional parameter called path is added to it to specify the path for the mapping received.To set that value to a variable we use @Path annotation with that variable.

@Service: We specify a class with @Service to indicate that they’re holding the business logic.The utility classes can be marked as Service classes.

@Repository: Responsible for data access.We specify a class or interface with @Repository to indicate that they’re dealing with CRUD operations, usually, it’s used with DAO (Data Access Object) or Repository implementations that deal with database tables.

@component

@Service

@Entity-used to mark the class which will form a table in the database.All Entity annotated classes should have one no argument constructor.

MVC Structure- Model-View-Controller design pattern.
Model: The Model encapsulates the application data.
View: View renders the model data and also generates HTML output that the client’s browser can interpret.
Controller: The Controller processes the user requests and passes them to the view for rendering.

/*I still haven't understood this*/ @Configuration annotation which indicates that the class has @Bean definition methods

A JavaBean is a Java class that should follow the following conventions i.e. It should have a no-arg constructor.
It should be Serializable and should provide methods to set and get the values of the properties, known as getter and setter methods.
Java bean is a reusable software component. A bean encapsulates many objects into one object. So that we can access this object from multiple places across the application.

POJO stands for Plain Old Java Object









