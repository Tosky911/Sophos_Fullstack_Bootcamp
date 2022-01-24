import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home/home/home.component';
import { AddProductComponent } from './product/add-product/add-product.component';
import { MoneyComponent } from './product/money/money.component';
import { CreateTransactionComponent } from './transaction/create-transaction/create-transaction.component';
import { ListTransactionComponent } from './transaction/list-transaction/list-transaction.component';
import { AddUserComponent } from './user/add-user/add-user.component';
import { DeleteUserComponent } from './user/delete-user/delete-user.component';
import { EditUserComponent } from './user/edit-user/edit-user.component';
import { ListUserComponent } from './user/list-user/list-user.component';

const routes: Routes = [
  {path:'', pathMatch: 'full', redirectTo: 'app-home'},
  //Redireccion a la pagina inicial
  {path:'app-home', component:HomeComponent},
  // To list all the users
  {path:'users', component:ListUserComponent},
  // To create a user
  {path:'addUser', component:AddUserComponent},
  // To edit a user info
  {path:'users/:id/products', component:EditUserComponent},
  //To delete a user
  {path:'users/:id/products', component:DeleteUserComponent},
  //To create a product
  {path:'users/:id/products/add', component:AddProductComponent},
  //To make movement of money
  {path:'users/:id/products/:productId/money', component:MoneyComponent},
  //To list all the transaction of a product
  {path:'users/:id/products/:productId/transactions', component:ListTransactionComponent},
  //To create a transaction
  {path:'users/:id/products/:productId/transaction', component:CreateTransactionComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
