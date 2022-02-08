import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Product } from 'src/app/models/product';
import { ProductService } from 'src/app/services/services/product/product.service';
import { CustomerService } from 'src/app/services/services/customer/customer.service';
@Component({
  selector: 'app-list-product',
  templateUrl: './list-product.component.html',
  styleUrls: ['./list-product.component.css']
})
export class ListProductComponent implements OnInit {

  products?: Product[];
  currentProduct: Product = {};
  currentIndex = -1;

  constructor(private productService:ProductService,
    private customerService: CustomerService,
    private route: ActivatedRoute,
    private router:Router) { }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params=> {
      if (params.has("id")){
        this.productService.getProduct(params.get("id")).subscribe(resp =>this.products = resp.data);
      }
    })
  }

  AddProduct(id: any): void{
    this.route.paramMap.subscribe(params=> {
      if (params.has("id")){        
        this.router.navigate(["customers/",params.get("id"),"products","add"]);
      }
    })
    
  }

  backProduct(): void {
    this.route.paramMap.subscribe((params) => {
      this.router.navigate(['customers', params.get('id'), 'products']);
    });

  }

  addMovementMoney(customerId:any, productId:any): void{
    console.log(customerId);
    //this.router.navigate(["customers/",customerId,"products",productId,"transaction"])
    this.router.navigate(["customers/"+customerId+"/products/"+productId+"/transaction"])
  
  }

  accountStatus(customerId:any, productId:any): void{
    this.router.navigate(["customers/",customerId,"products",productId,"transactions"])
  }

  activateProduct(customerId:any, productId:any): void{
    this.productService.activateProduct(customerId, productId, this.currentProduct)
    .subscribe({
      next: (res) => {
        alert("El estado del producto fue actualizado con éxito");
        this.route.paramMap.subscribe(params=> {
        this.productService.getProduct(params.get("id")).subscribe(resp =>this.products = resp.data);})
        this.route.paramMap.subscribe(params=> {
        this.router.navigate(["customers/",params.get("id"),"products"]);})
      },
      error: (e: any) => console.error(e)
    });
    this.route.paramMap.subscribe(params=> {
      this.router.navigate(["customers/"]);
      if (params.has("id")){        
      this.router.navigate(["customers/",params.get("id"),"products"]);
      }
    })
  }

  deactivateProduct(customerId:any, productId:any): void{
    this.productService.deactivateProduct(customerId, productId, this.currentProduct)
    .subscribe({
      next: (res) => {
        alert("El estado del producto fue actualizado con éxito");
        this.route.paramMap.subscribe(params=> {
        this.productService.getProduct(params.get("id")).subscribe(resp =>this.products = resp.data);})
        this.route.paramMap.subscribe(params=> {
        this.router.navigate(["customers/",params.get("id"),"products"]);})
      },
      error: (e: any) => console.error(e)
    });
    this.route.paramMap.subscribe(params=> {
      this.router.navigate(["customers/"]);
      if (params.has("id")){        
      this.router.navigate(["customers/",params.get("id"),"products"]);
      }
    })
  }

  cancelProduct(customerId:any, productId:any): void{
    this.productService.cancelProduct(customerId, productId, this.currentProduct)
    .subscribe({
      next: (res) => {

        alert("Actualización realizada con éxito");
        this.route.paramMap.subscribe(params=> {
          this.productService.getProduct(params.get("id")).subscribe(resp =>this.products = resp.data);})
        this.route.paramMap.subscribe(params=> {
          this.router.navigate(["customers/",params.get("id"),"products"]);
          this.router.routeReuseStrategy.shouldReuseRoute = function() { return false; };
        })
      },
      error: (e) => console.error(e)
    });
  }

  uncancelProduct(customerId:any, productId:any): void{
    this.productService.uncancelProduct(customerId, productId, this.currentProduct)
    .subscribe({
      next: (res) => {

        alert("Actualización realizada con éxito. Se ha reestablecido la cuenta.");
        this.route.paramMap.subscribe(params=> {
          this.productService.getProduct(params.get("id")).subscribe(resp =>this.products = resp.data);})
        this.route.paramMap.subscribe(params=> {
          this.router.navigate(["customers/",params.get("id"),"products"]);
          this.router.routeReuseStrategy.shouldReuseRoute = function() { return false; };
        })
      },
      error: (e) => console.error(e)
    });
  }

}