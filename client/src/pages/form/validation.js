import React, { useState } from "react";
import Header from '../../components/header/header';
import MenuLeft from '../../components/menu/menu-left';
import { useForm } from 'react-hook-form'

export default function Validation() {

    const [collapsemenu, setCollapsemenu] = useState(false);

    const changeMenu = () => {
        setCollapsemenu(!collapsemenu);
    }

    const { register, handleSubmit, watch, errors } = useForm();
    const onSubmit = data => { console.log(data) }
    // console.log(watch('email'))

    return (
        <div className={collapsemenu ? 'sidebar-mini layout-fixed sidebar-collapse' : 'wrapper'}>
            <Header changeMenu={changeMenu} />
            <MenuLeft />
            <div class="content-wrapper">
                {/*  Content Header (Page header)  */}
                <section class="content-header">
                    <div class="container-fluid">
                        <div class="row mb-2">
                            <div class="col-sm-6">
                                <h1>Validation</h1>
                            </div>
                            <div class="col-sm-6">
                                <ol class="breadcrumb float-sm-right">
                                    <li class="breadcrumb-item"><a href="#">Home</a></li>
                                    <li class="breadcrumb-item active">Validation</li>
                                </ol>
                            </div>
                        </div>
                    </div>{/*  /.container-fluid  */}
                </section>

                {/*  Main content  */}
                <section class="content">
                    <div class="container-fluid">
                        <div class="row">
                            {/*  left column  */}
                            <div class="col-md-12">
                                {/*  jquery validation  */}
                                <div class="card card-primary">
                                    <div class="card-header">
                                        <h3 class="card-title">Quick Example <small>jQuery Validation</small></h3>
                                    </div>
                                    {/*  /.card-header  */}
                                    {/*  form start  */}
                                    <form role="form" id="quickForm" onSubmit={handleSubmit(onSubmit)}>
                                        <div class="card-body">
                                            <div class="form-group">
                                                <label for="exampleInputEmail1">Email address</label>
                                                <input name="email" class="form-control" id="exampleInputEmail1" placeholder="Enter email" ref={register({ required: true, email: true })} />
                                                {errors.email && <span>This field is required</span>}
                                            </div>
                                            <div class="form-group">
                                                <label for="exampleInputPassword1">Password</label>
                                                <input type="password" name="password" class="form-control" id="exampleInputPassword1" placeholder="Password" ref={register({ required: true })} />
                                                {errors.password && <span>This field is required</span>}
                                            </div>
                                            <div class="form-group mb-0">
                                                <div class="custom-control custom-checkbox">
                                                    <input type="checkbox" name="terms" class="custom-control-input" id="exampleCheck1" />
                                                    
                                                    <label class="custom-control-label" for="exampleCheck1">I agree to the <a href="#">terms of service</a>.</label>
                                                </div>
                                            </div>
                                        </div>
                                        {/*  /.card-body  */}
                                        <div class="card-footer">
                                        {/* <input type="submit" /> */}
                                            <button type="submit" class="btn btn-primary">Submit</button>
                                        </div>
                                    </form>
                                </div>
                                {/*  /.card  */}
                            </div>
                            {/* /.col (left)  */}
                            {/*  right column  */}
                            <div class="col-md-6">

                            </div>
                            {/* /.col (right)  */}
                        </div>
                        {/*  /.row  */}
                    </div>{/*  /.container-fluid  */}
                </section>
                {/*  /.content  */}
            </div>
        </div>
    )
}