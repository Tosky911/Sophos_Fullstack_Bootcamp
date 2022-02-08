export class Transaction{
    transactionId?:number;
	
	//Valor con el que se asocia a la entidad Product
	principalProductId?:number;
    principalUserId?: number;

	secondaryProductId?:number;
    secondayUserId?: number;
	
	//Tipo transaccion
	transactionType?: String;
	
	//Valor de la transaccion
	transactionValue?: number;
	
	//Fecha de transaccion
	transactionDate?: String;
	
	//Detalles de transaccion
	transactionDetails?: String;
	
	//Resultado de la transaccion
	transactionResult?: String;
	
	//Saldo final de la transaccion
	finalBalance?: number;
	
	//GMF impuesto
	GMF?: number;
	
	//Movimiento financiero
	financeMovement?: String;
}