import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { User } from 'src/app/entities/user';
import { UserService } from 'src/app/services/services/user/user.service';

@Component({
  selector: 'app-edit-user',
  templateUrl: './edit-user.component.html',
  styleUrls: ['./edit-user.component.css']
})
export class EditUserComponent implements OnInit {

   currentUser: User = {
    typeId: '',
    numId: '',
    firstName: '',
    lastName: '',
    email: '',
    birthdayDate: '',
    creationDate: '',
  };
  
  constructor(
    private userService: UserService,
    private route: ActivatedRoute,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params=> {
      if (params.has("id")){
        this.userService.listOneCustomerId(params.get("id")).subscribe(resp =>this.currentUser = resp.data);
      }
    })
  }
  
  list(id: any): void{
    this.userService.listOneCustomerId(id)
    .subscribe({
      next: (resp)=>{
        this.currentUser = resp.data;
      },
      error: (e) => console.error(e)
    });
  }
  
  edit(): void{
    this.userService.edit(this.currentUser.id, this.currentUser)
    .subscribe({
      next: (res) => {
        alert("La información del cliente fue actualizada con éxito. Será redirigido al panel de clientes");
        this.router.navigate(['/users']);
      },
      error: (e) => console.error(e)
    });
  }
  
  delete(): void{
    this.userService.delete(this.currentUser.id)
    .subscribe({
      next: (res) => {
        this.router.navigate(['/users']);
      },
      error: (e) => console.error(e)
    });
  }
  
  backUser(): void{
    this.router.navigate(['/users']);
  }
}
