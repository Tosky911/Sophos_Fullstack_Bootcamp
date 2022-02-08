import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './core/home/home.component';
import { AddProductComponent } from './product/add-product/add-product.component';
import { MoneyComponent } from './product/money/money.component';
import { CreateTransactionComponent } from './transaction/create-transaction/create-transaction.component';
import { ListTransactionComponent } from './transaction/list-transaction/list-transaction.component';
import { AddCustomerComponent } from './customer/add-customer/add-customer.component';
import { DeleteCustomerComponent } from './customer/delete-customer/delete-customer.component';
import { EditCustomerComponent } from './customer/edit-customer/edit-customer.component';
import { ListCustomerComponent } from './customer/list-customer/list-customer.component';

const routes: Routes = [
  {path:'', pathMatch: 'full', redirectTo: 'app-home'},
  //Redireccion a la pagina inicial
  {path:'app-home', component:HomeComponent},
  // To list all the customers
  {path:'customers', component:ListCustomerComponent},
  // To create a customer
  {path:'addCustomer', component:AddCustomerComponent},
  // To edit a customer info
  {path:'customers/:id/products', component:EditCustomerComponent},
  //To delete a customer
  {path:'customers/:id/products', component:DeleteCustomerComponent},
  //To create a product
  {path:'customers/:id/products/add', component:AddProductComponent},
  //To make movement of money
  {path:'customers/:id/products/:productId/money', component:MoneyComponent},
  //To list all the transaction of a product
  {path:'customers/:id/products/:productId/transactions', component:ListTransactionComponent},
  //To create a transaction
  {path:'customers/:id/products/:productId/transaction', component:CreateTransactionComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
