import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Customer } from 'src/app/models/customer';
import { CustomerService } from 'src/app/services/services/customer/customer.service';

@Component({
  selector: 'app-edit-customer',
  templateUrl: './edit-customer.component.html',
  styleUrls: ['./edit-customer.component.css']
})
export class EditCustomerComponent implements OnInit {

   currentCustomer: Customer = {
    typeId: '',
    numId: '',
    firstName: '',
    lastName: '',
    email: '',
    birthdayDate: '',
    creationDate: '',
  };
  
  constructor(
    private customerService: CustomerService,
    private route: ActivatedRoute,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params=> {
      if (params.has("id")){
        this.customerService.listOneCustomerId(params.get("id")).subscribe(resp =>this.currentCustomer = resp.data);
      }
    })
  }
  
  list(id: any): void{
    this.customerService.listOneCustomerId(id)
    .subscribe({
      next: (resp)=>{
        this.currentCustomer = resp.data;
      },
      error: (e) => console.error(e)
    });
  }
  
  edit(): void{
    this.customerService.edit(this.currentCustomer.id, this.currentCustomer)
    .subscribe({
      next: (res) => {
        alert("La información del cliente fue actualizada con éxito. Será redirigido al panel de clientes");
        this.router.navigate(['/customers']);
      },
      error: (e) => console.error(e)
    });
  }
  
  delete(): void{
    this.customerService.delete(this.currentCustomer.id)
    .subscribe({
      next: (res) => {
        this.router.navigate(['/customers']);
      },
      error: (e) => console.error(e)
    });
  }
  
  backCustomer(): void{
    this.router.navigate(['/customers']);
  }
}
