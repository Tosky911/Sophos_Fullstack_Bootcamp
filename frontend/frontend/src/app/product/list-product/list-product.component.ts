import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Product } from 'src/app/entities/product';
import { ProductService } from 'src/app/services/services/product/product.service';
import { UserService } from 'src/app/services/services/user/user.service';

@Component({
  selector: 'app-list-product',
  templateUrl: './list-product.component.html',
  styleUrls: ['./list-product.component.css']
})
export class ListProductComponent implements OnInit {

  products?:Product[];
  currentProduct: Product = {};
  currentIndex = -1;

  constructor(private productService:ProductService,
    private userService: UserService, 
    private route: ActivatedRoute,
    private router:Router) { }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params=> {
      if (params.has("id")){
        this.productService.getProduct(params.get("id")).subscribe(data =>this.products = data);
      }
    })
  }

  AddProduct(id: any): void{
    this.route.paramMap.subscribe(params=> {
      if (params.has("id")){        
        this.router.navigate(["users/",params.get("id"),"products","add"]);
      }
    })
    
  }

  addMovementMoney(userId:any, productId:any): void{
    this.router.navigate(["users/",userId,"products",productId,"transaction"])
    
  }

  accountStatus(userId:any, productId:any): void{
    this.router.navigate(["users/",userId,"products",productId,"transactions"])
    
  }

  updateProduct(userId:any, productId:any): void{
    this.productService.updateStateProduct(userId, productId, this.currentProduct)
    .subscribe({
      next: (res) => {
        alert("El estado del producto fue actualizado con éxito");
        this.route.paramMap.subscribe(params=> {
        this.productService.getProduct(params.get("id")).subscribe(data =>this.products = data);})
        this.route.paramMap.subscribe(params=> {
        this.router.navigate(["users/",params.get("id"),"products"]);})
      },
      error: (e) => console.error(e)
    });
    this.route.paramMap.subscribe(params=> {
      this.router.navigate(["users/"]);
      if (params.has("id")){        
      this.router.navigate(["users/",params.get("id"),"products"]);
      }
    })
  }

  cancelProduct(userId:any, productId:any): void{
    this.productService.cancelStateProduct(userId, productId, this.currentProduct)
    .subscribe({
      next: (res) => {

        alert("Actualización realizada con éxito. Mientras la cuenta tenga saldo no podrá ser cancelada");
        this.route.paramMap.subscribe(params=> {
          this.productService.getProduct(params.get("id")).subscribe(data =>this.products = data);})
        this.route.paramMap.subscribe(params=> {
          this.router.navigate(["users/",params.get("id"),"products"]);
          this.router.routeReuseStrategy.shouldReuseRoute = function() { return false; };
        })
      },
      error: (e) => console.error(e)
    });
  }
}
