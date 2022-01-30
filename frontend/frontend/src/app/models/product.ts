export class Product{
    productId?:number;
	
    //Id con el que se asocia a un User
	userId?:number;
	
	//Saldo de la cuenta
	balance?:number;
	
	//Tipo de cuenta
	typeAccount?: String;
	
	//Numero de cuenta
	numAccount?: String;
	
	//Creacion de cuenta
	creationDate?: String;
	
	//Estado de la cuenta
	state?: String;
}