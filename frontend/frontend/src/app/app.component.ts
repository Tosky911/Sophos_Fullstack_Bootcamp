import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'BankApp';

  constructor(private router:Router){}

  ngOnInit(): void {
    this.router.navigate(['']);
  }

  List(){
    this.router.navigate(['users']);
  }

  New(){
    this.router.navigate(['addUser']);
  }

}
