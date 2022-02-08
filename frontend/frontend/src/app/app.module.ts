import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';

import { AppComponent } from './app.component';
import { AddCustomerComponent } from './customer/add-customer/add-customer.component';
import { CustomerService } from './services/services/customer/customer.service';
import { HttpClientModule } from '@angular/common/http';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { DeleteCustomerComponent } from './customer/delete-customer/delete-customer.component';
import { EditCustomerComponent } from './customer/edit-customer/edit-customer.component';
import { ListCustomerComponent } from './customer/list-customer/list-customer.component';
import { AddProductComponent } from './product/add-product/add-product.component';
import { DeleteProductComponent } from './product/delete-product/delete-product.component';
import { ListProductComponent } from './product/list-product/list-product.component';
import { MoneyComponent } from './product/money/money.component';
import { CreateTransactionComponent } from './transaction/create-transaction/create-transaction.component';
import { ListTransactionComponent } from './transaction/list-transaction/list-transaction.component';
import { HomeComponent } from './core/home/home.component';
import { LoginComponent } from './core/login/login.component';
@NgModule({
  declarations: [
    AppComponent,
    AddCustomerComponent,
    DeleteCustomerComponent,
    EditCustomerComponent,
    ListCustomerComponent,
    AddProductComponent,
    DeleteProductComponent,
    ListProductComponent,
    MoneyComponent,
    CreateTransactionComponent,
    ListTransactionComponent,
    HomeComponent,
    LoginComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    NgbModule
  ],
  providers: [CustomerService],
  bootstrap: [AppComponent]
})
export class AppModule { }
function routes(routes: any, arg1: { onSameUrlNavigation: "reload"; }): any[] | import("@angular/core").Type<any> | import("@angular/core").ModuleWithProviders<{}> {
  throw new Error('Function not implemented.');
}
