import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Transaction } from 'src/app/models/transaction';
import { TransactionService } from 'src/app/services/services/transaction/transaction.service';

@Component({
  selector: 'app-list-transaction',
  templateUrl: './list-transaction.component.html',
  styleUrls: ['./list-transaction.component.css']
})
export class ListTransactionComponent implements OnInit {

  transactions?: Transaction[];

  constructor(
    private transactionService: TransactionService, 
    private route: ActivatedRoute,
    private router:Router) { }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params=> {
      if (params.has("id")){
        this.transactionService.getTransaction(params.get("id"),params.get("productId")).subscribe(data =>this.transactions = data);
      }
    })
  }

  backProduct(): void {
    this.route.paramMap.subscribe((params) => {
      this.router.navigate(['users', params.get('id'), 'products']);
    });

  }
}
