import { formatDate } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Product } from 'src/app/models/product';
import { Transaction } from 'src/app/models/transaction';
import { TransactionService } from 'src/app/services/services/transaction/transaction.service';

@Component({
  selector: 'app-create-transaction',
  templateUrl: './create-transaction.component.html',
  styleUrls: ['./create-transaction.component.css']
})
export class CreateTransactionComponent implements OnInit {

  dateNow = new Date();

  transaction: Transaction={
    secondaryProductId:0,
    transactionType: '' ,
    transactionValue:0 ,
    transactionDate: '',
    transactionDetails:'',
    transactionResult:'',
    finalBalance:0 ,
    GMF:0 ,
    financeMovement:'',
  };

  isCollapsed(): boolean {
    if (this.transaction.transactionType != 'Transferencia' )
      return true;
    else
      return false;
  }

  products?: Product[];

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private transactionService: TransactionService
  ) { }

  ngOnInit(): void {  
    this.route.paramMap.subscribe(params=> {
      if (params.has("id")){
        this.transactionService.getProduct(params.get("id"),params.get("productId")).subscribe(data =>this.products = data);
      }
    })
  }

  saveTransaction(): void {
    const data = {
      secondaryProductId:this.transaction.secondaryProductId,
      transactionType: this.transaction.transactionType ,
      transactionValue:this.transaction.transactionValue ,
      transactionDate: formatDate(this.dateNow, 'YYYY-MM-dd', 'en-US'),
      transactionDetails:this.transaction.transactionDetails
    };

    this.route.paramMap.subscribe((params) => {
        this.transactionService.createTransaction(data, params.get('id'), params.get('productId')).subscribe(
          {
          next: () => {
            console.log(data)
            alert("Transacción realizada con éxito");
            this.router.navigate(['users', params.get('id'), 'products',params.get('productId'),'transactions']);
          },
          error: (e) => console.error(e),
        });
      });
  }

  backProduct(): void {
    this.route.paramMap.subscribe((params) => {
      this.router.navigate(['users', params.get('id'), 'products']);
    });

  }

}
