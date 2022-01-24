import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';

import { AppComponent } from './app.component';
import { AddUserComponent } from './user/add-user/add-user.component';
import { UserService } from './services/services/user/user.service';
import { HttpClientModule } from '@angular/common/http';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { DeleteUserComponent } from './user/delete-user/delete-user.component';
import { EditUserComponent } from './user/edit-user/edit-user.component';
import { ListUserComponent } from './user/list-user/list-user.component';
import { AddProductComponent } from './product/add-product/add-product.component';
import { DeleteProductComponent } from './product/delete-product/delete-product.component';
import { ListProductComponent } from './product/list-product/list-product.component';
@NgModule({
  declarations: [
    AppComponent,
    AddUserComponent,
    DeleteUserComponent,
    EditUserComponent,
    ListUserComponent,
    AddProductComponent,
    DeleteProductComponent,
    ListProductComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    NgbModule
  ],
  providers: [UserService],
  bootstrap: [AppComponent]
})
export class AppModule { }
function routes(routes: any, arg1: { onSameUrlNavigation: "reload"; }): any[] | import("@angular/core").Type<any> | import("@angular/core").ModuleWithProviders<{}> {
  throw new Error('Function not implemented.');
}
