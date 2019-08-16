import { NgModule }      from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppComponent }  from './app.component';
import { UpdateAccountClass } from './app.addProduct';
import { FormsModule} from '@angular/forms';
import { HttpClientModule} from '@angular/common/http';
import { RouterModule,Routes } from '@angular/router';
import { SearchClass } from './app.search';
import { ProductViewClass } from './app.ProductView';


    const routes: Routes = [
        { path: '', redirectTo: 'Product', pathMatch: 'full' },
       // { path: 'Account', component: CustomersListComponent },
        { path: 'add', component:UpdateAccountClass },//SearchClass
        { path: 'show', component:SearchClass },//CustomerListClass
        {  path: 'find', component:ProductViewClass } 
      //  { path: 'findbyage', component: SearchCustomersComponent },
       // {path:'operation', component: OperationComponent},
        
    ];

@NgModule({
    imports: [
        BrowserModule, RouterModule.forRoot(routes),HttpClientModule,FormsModule    
    ],
    
    declarations: [
        AppComponent, UpdateAccountClass, SearchClass, ProductViewClass
    
		],
    providers: [ ],
    bootstrap: [AppComponent]
})

export class AppModule { }