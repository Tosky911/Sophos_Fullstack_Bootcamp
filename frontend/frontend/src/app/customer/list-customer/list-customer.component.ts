import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Customer } from 'src/app/models/customer';
import { CustomerService } from 'src/app/services/services/customer/customer.service';

@Component({
  selector: 'app-list-customer',
  templateUrl: './list-customer.component.html',
  styleUrls: ['./list-customer.component.css']
})
export class ListCustomerComponent implements OnInit {

  customers: Customer[] = [];
  currentCustomer: Customer ={};

  constructor(private service:CustomerService, private router:Router) { }

  ngOnInit(): void {
    this.getCustomers();
  }

  getCustomers(){
    this.service.get().subscribe(resp=>{this.customers= resp.data});
  }

  setActiveCustomer(customer: Customer): void{
    this.currentCustomer = customer;
  }

  EditCustomer(customerId: number | undefined): void{
    this.router.navigate(["customers/",customerId,"products"]);
  }

}
