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
    username: '',
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
        this.userService.getUserId(params.get("id")).subscribe(data =>this.currentUser = data);
      }
    })
  }
  
  getUser(id: String): void{
    this.userService.getUserId(id)
    .subscribe({
      next: (data)=>{
        this.currentUser = data;
      },
      error: (e) => console.error(e)
    });
  }
  
  updateUser(): void{
    this.userService.updateUser(this.currentUser.id, this.currentUser)
    .subscribe({
      next: (res) => {
        alert("La información del usuario fue actualizada con éxito. Será redirigido al Panel de Usuarios");
        this.router.navigate(['/users']);
      },
      error: (e) => console.error(e)
    });
  }
  
  deleteUser(): void{
    this.userService.deleteUser(this.currentUser.id)
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
