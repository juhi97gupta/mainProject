
import { Component, OnInit } from "@angular/core";
import { Product } from "./app.Product";
 import { ProductService } from "./app.ProductServiceClass";
import { Observable } from 'rxjs';

@Component({
    selector: 'list-comp',
    templateUrl: 'viewAll.html'
})

export class ProductViewClass implements OnInit{

    products: Observable<Product[]>;

   constructor(private service: ProductService) { }

    ngOnInit() {
        this.reloadData();
      }
      reloadData() {
        // this.accounts = this.service.getCustomersList();
        // console.log(this.accounts)
      }

}
