import { formatDate } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Product } from 'src/app/entities/product';
import { User } from 'src/app/entities/user';
import { ProductService } from 'src/app/services/services/product/product.service';

@Component({
  selector: 'app-add-product',
  templateUrl: './add-product.component.html',
  styleUrls: ['./add-product.component.css']
})
export class AddProductComponent implements OnInit {
  
  dateNow = new Date();

  products?: Product[];
  users?: User[];

  product: Product = {
    typeAccount: '',
    numAccount: '',
    creationDate: '',
    state: '',
    balance: 0,
  };
  save = false;

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private productService: ProductService
  ) {}

  ngOnInit(): void {}

  saveProduct(): void {
    const data = {
      typeAccount: this.product.typeAccount,
      numAccount: this.product.numAccount,
      state: 'activa',
      creationDate: formatDate(this.dateNow, 'YYYY-MM-dd', 'en-US'),
    };

    this.route.paramMap.subscribe((params) => {
      if (params.has('id')) {
        this.productService
          .getProduct(params.get('id'))
          .subscribe(resp => (this.products = resp.data));
        this.productService.createProduct(data, params.get('id')).subscribe({
          next: () => {
            alert('El producto ha sido creado con éxito');
            this.router.navigate(['users', params.get('id'), 'products']);
          },
          error: (e) => console.error(e),
        });
      }
    });
  }

  backProduct(): void {
    this.route.paramMap.subscribe((params) => {
      this.router.navigate(['users', params.get('id'), 'products']);
    });

  }
}
