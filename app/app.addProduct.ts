import { Component, OnInit } from "@angular/core";
import { Product } from "./app.Product";
import { ProductService } from "./app.ProductServiceClass";



@Component({
    selector: 'add-comp',
    templateUrl: 'addProduct.html'
})

export class UpdateAccountClass implements OnInit{
  ob: Product = new Product();
  submitted = false;
constructor(private  service: ProductService){}

//@Input() ob: Product;

productID: number;
productName: string;
merchantId: number;
tag: string[];
company: string;
photo: string;
description: string;
quantity: number;
category: string;
subcategory: string;
soldQuantities: number;
price: number;
releaseDate: Date;

ngOnInit() {
}
AddData(){
   this.service. createAccount(this.ob)
  .subscribe(data => console.log(data), error => console.log(error));
 this.ob = new Product();
}

newProduct(): void {
  this.submitted = false;
  this.ob = new Product();
}

onSubmit() {
  this.submitted = true;
  this.AddData();
}
}
  